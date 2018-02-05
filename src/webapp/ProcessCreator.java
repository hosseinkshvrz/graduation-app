package webapp;

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
import java.util.Scanner;


@WebServlet(name = "ProcessCreator")
public class ProcessCreator extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = "";
        Scanner scanner = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        System.out.println(json);
        JSONArray readingJSONArray;
        try {
            readingJSONArray = new JSONArray(json);
            Process process;
            String processName = readingJSONArray.getJSONObject(0).getString("processName");
            process = new Process(processName);
            processTable.addNewProcessToDB(process);
            for (int i = 1; i < readingJSONArray.length(); i++) {
                String stepName = readingJSONArray.getJSONObject(i).getString("stepName");
                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
                int rejectStepID = readingJSONArray.getJSONObject(i).getInt("rejectStepID");
                int departmentID = readingJSONArray.getJSONObject(i).getInt("departmentID");
                boolean isFirstStep = readingJSONArray.getJSONObject(i).getBoolean("isFirstStep");
                Step step = new Step(stepName, acceptStepID, rejectStepID, process.getID(), departmentID, isFirstStep);
                stepTable.addNewStepToDB(step);
                process.addStep(step);
                if (isFirstStep) {
                    process.setFirstStep(step);
                }
            }
            processTable.addStepsToProcess(process);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }
        String responseMessage = "success";
        //handle graph
        JSONObject sendingJSONObject = new JSONObject();
        try {
            sendingJSONObject.put("responseMessage", responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(sendingJSONObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
