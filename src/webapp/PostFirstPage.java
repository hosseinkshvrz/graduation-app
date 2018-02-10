package webapp;

import appLayer.steps.Step;
import appLayer.steps.StepInstance;
import appLayer.users.StudentUser;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.steps.StepInstanceDatabase;
import datalayer.tables.users.StudentDatabase;
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


@WebServlet(name = "PostFirstPage")
public class PostFirstPage extends HttpServlet {
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private StepDatabase stepTable = new StepDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        JSONArray sendingJSONArray = new JSONArray();
        try {
            String personnelID = readingJSONObject.getString("personnelID");
            ArrayList<StepInstance> postStepInstances = stepInstanceTable.getPostRelatedStepInstances(personnelID);
            for (StepInstance si :
                    postStepInstances) {
                JSONObject sendingJSONObject = new JSONObject();
                int stepID = si.getStepID();
                Step step = stepTable.getStep(stepID);
                StudentUser student = studentTable.getStudentInStepInstance(si.getStepInstanceID());
                sendingJSONObject.put("studentID", student.getStudentID());
                sendingJSONObject.put("studentName", student.getFirstName() + " " + student.getLastName());
                sendingJSONObject.put("stepName", step.getStepName());
                sendingJSONArray.put(sendingJSONObject);
            }
            io.sendJSONArray(sendingJSONArray, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}