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
import java.util.HashMap;
import java.util.Scanner;


@WebServlet(name = "ProcessCreator")
public class ProcessCreator extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            //TODO get firstStepID and add to graph array list. then check the cycle of accept steps
//            ArrayList<Node> graph = new ArrayList<>();
//            for (int i = 1; i < readingJSONArray.length(); i++) {
//                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
//            }

            Process process;
            String processName = readingJSONObject.getString("processName");
            process = new Process(processName);
            processTable.addNewProcessToDB(process);
            String temp = readingJSONObject.getString("steps");
            JSONArray readingJSONArray = new JSONArray(temp);
            HashMap<Integer, String> acceptSteps = new HashMap<>();
            HashMap<Integer, String> rejectSteps = new HashMap<>();
            for (int i = 0; i < readingJSONArray.length(); i++) {
                String stepName = readingJSONArray.getJSONObject(i).getString("stepName");
                String acceptStepName = readingJSONArray.getJSONObject(i).getString("afterAcceptStepName");
                String rejectStepName = readingJSONArray.getJSONObject(i).getString("afterRejectStepName");
                String departmentID = readingJSONArray.getJSONObject(i).getString("departmentID");

                boolean isFirstStep = readingJSONArray.getJSONObject(i).getBoolean("isFirstStep");
                Step step = new Step(stepName, process.getID(), departmentID, isFirstStep);
                stepTable.addNewStepToDB(step);
                acceptSteps.put(step.getStepID(), acceptStepName);
                rejectSteps.put(step.getStepID(), rejectStepName);
                process.addStep(step);
                if (isFirstStep) {
                    process.setFirstStep(step);
                }
            }
            for (Integer key :
                    acceptSteps.keySet()) {
                if (!acceptSteps.get(key).equals("")) {
                    Integer acceptStepID = stepTable.getStep(acceptSteps.get(key)).getStepID();
                    stepTable.setAcceptStepID(key, acceptStepID);
                }
            }
            for (Integer key :
                    acceptSteps.keySet()) {
                if (!rejectSteps.get(key).equals("")) {
                    Integer rejectStepID = stepTable.getStep(rejectSteps.get(key)).getStepID();
                    stepTable.setRejectStepID(key, rejectStepID);
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
