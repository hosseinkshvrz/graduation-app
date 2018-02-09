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
import java.util.ArrayList;


@WebServlet(name = "ProcessesGetter")
public class ProcessesGetter extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray sendingJsonArray = new JSONArray();
        ArrayList<Process> allProcesses = processTable.getProcesses();
        for (int i = 0; i < allProcesses.size(); i++) {
            JSONObject processJSONObject = new JSONObject();
            JSONArray stepsJSONArray = new JSONArray();
            Process process = allProcesses.get(i);
            try {
                processJSONObject.put("processName", process.getName());
                for (int j = 0; j < process.getProcessSteps().size(); j++) {
                    JSONObject stepJSONObject = new JSONObject();
                    Step step = process.getProcessSteps().get(j);
                    stepJSONObject.put("stepName", step.getStepName());
                    stepJSONObject.put("departmentID", step.getDepartmentID());
                    //Accept Step Name
                    stepJSONObject.put("afterAcceptStepName", stepTable.getStep(step.getAcceptStepID()).getStepName());
                    //Reject Step Name
                    stepJSONObject.put("afterRejectStepName", stepTable.getStep(step.getRejectStepID()).getStepName());
                    stepsJSONArray.put(stepJSONObject);
                }
                processJSONObject.put("steps", stepsJSONArray);
                sendingJsonArray.put(processJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        InputOutputHandler io = new InputOutputHandler();
        io.sendJSONArray(sendingJsonArray, response);
    }
}
