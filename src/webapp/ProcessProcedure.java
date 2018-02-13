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
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            ArrayList<StepInstance> studentStepInstances = stepInstanceTable.getStudentSteps(studentID);
            JSONArray sendingJSONArray = new JSONArray();
            JSONObject sendingJSONObject = new JSONObject();
            for (StepInstance si :
                    studentStepInstances) {
                JSONObject tempJSONObject = new JSONObject();
                int stepID = si.getStepID();
                String stepName = stepTable.getStep(stepID).getStepName();
                tempJSONObject.put("stepInstanceName", stepName);
                tempJSONObject.put("startDate", si.getStart());
                String endDate = si.getEnd();
                if (endDate == null) {
                    endDate = "0-0-0";
                }
                tempJSONObject.put("endDate", endDate);
                String status = si.getResult();
                if (status.equals("stall")) {
                    status = "در حال بررسی";
                }
                else if (status.equals("no")) {
                    status = "رد شده";
                }
                else if (status.equals("yes")) {
                    status = "پذیرفته شده";
                }
                tempJSONObject.put("status", status);
                sendingJSONArray.put(tempJSONObject);
            }
            sendingJSONObject.put("responseMessage", "success");
            sendingJSONObject.put("stepInstances", sendingJSONArray);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
