package webapp;

import appLayer.Process;
import appLayer.Step;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import datalayer.tables.ProcessDatabase;
import datalayer.tables.StepDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "ProcessesGetter")
public class ProcessesGetter extends HttpServlet {
    private ProcessDatabase processTable;
    private StepDatabase stepTable = new StepDatabase();
    public ProcessesGetter() {
        processTable = new ProcessDatabase();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray processesJsonArray = new JSONArray();

        try {
            ArrayList<Process> allProcesses = processTable.getProcesses();
            for (int i = 0; i < allProcesses.size(); i++) {
                JSONObject processJsonObject = new JSONObject();
                JSONArray steps = new JSONArray();
                Process process = allProcesses.get(i);
                try {
                    processJsonObject.put("processName", process.getName());

                    for (int j = 0; j < process.getProcessSteps().size(); j++) {
                        JSONObject stepObject = new JSONObject();
                        Step step = process.getProcessSteps().get(j);
                        stepObject.put("stepName", step.getStepName());
                        stepObject.put("departmentID", step.getDepartmentID());
                        //Accept Step Name
                        stepObject.put("afterAcceptStepName", stepTable.getStep(step.getAcceptStepID()).getStepName());
                        //Reject Step Name
                        stepObject.put("afterRejectStepName", stepTable.getStep(step.getRejectStepID()).getStepName());
                        steps.put(step);
                    }
                    processJsonObject.put("steps", steps);
                    processesJsonArray.put(processJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
