package webapp;

import appLayer.steps.StepInstance;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.steps.StepInstanceDatabase;
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


@WebServlet(name = "ProcessProcedure")
public class ProcessProcedure extends HttpServlet {
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            ArrayList<StepInstance> studentStepInstances = stepInstanceTable.getStudentSteps(studentID);
            JSONArray sendingJSONArray = new JSONArray();
            for (StepInstance si :
                    studentStepInstances) {
                JSONObject sendingJSONObject = new JSONObject();
                int stepID = si.getStepID();
                String stepName = stepTable.getStep(stepID).getStepName();
                sendingJSONObject.put("stepName", stepName);
                sendingJSONObject.put("startTime", si.getStart());
                sendingJSONObject.put("endTime", si.getEnd());
                sendingJSONObject.put("result", si.getResult());
                sendingJSONArray.put(sendingJSONObject);
            }
            io.sendJSONArray(sendingJSONArray, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
