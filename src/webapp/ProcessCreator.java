package webapp;

import appLayer.Node;
import appLayer.processes.Process;
import appLayer.steps.Step;
import datalayer.tables.processes.ProcessDatabase;
import datalayer.tables.steps.StepDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;


@WebServlet(name = "ProcessCreator")
public class ProcessCreator extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONArray readingJSONArray = io.getJSONArray(request);
        try {
            //TODO get firstStepID and add to graph array list. then check the cycle of accept steps
            ArrayList<Node> graph = new ArrayList<>();
            for (int i = 1; i < readingJSONArray.length(); i++) {
                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
            }

            Process process;
            String processName = readingJSONArray.getJSONObject(0).getString("processName");
            process = new Process(processName);
            processTable.addNewProcessToDB(process);
            for (int i = 1; i < readingJSONArray.length(); i++) {
                String stepName = readingJSONArray.getJSONObject(i).getString("stepName");
                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
                int rejectStepID = readingJSONArray.getJSONObject(i).getInt("rejectStepID");
                String departmentID = readingJSONArray.getJSONObject(i).getString("departmentID");
                //isFirstStep must be sent as true or false
                boolean isFirstStep = readingJSONArray.getJSONObject(i).getBoolean("isFirstStep");
                Step step = new Step(stepName, acceptStepID, rejectStepID, process.getID(), departmentID, isFirstStep);
                stepTable.addNewStepToDB(step);
                process.addStep(step);
                if (isFirstStep) {
                    process.setFirstStep(step);
                }
            }
            processTable.addStepsToProcess(process);
            String responseMessage = "success";
            JSONObject sendingJSONObject = new JSONObject();
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
