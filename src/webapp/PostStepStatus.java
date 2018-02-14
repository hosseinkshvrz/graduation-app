package webapp;

import appLayer.PostRequest;
import appLayer.users.PostUser;
import datalayer.tables.PostRequestsDatabase;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.steps.StepInstanceDatabase;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "PostStepStatus")
public class PostStepStatus extends HttpServlet {
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private StepDatabase stepTable = new StepDatabase();
    private PostRequestsDatabase postRequestsTable = new PostRequestsDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //personnelId
        //studentID
        //stepName
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String personnelID = readingJSONObject.getString("personnelID");
            String studentID = readingJSONObject.getString("studentID");
            String stepName = readingJSONObject.getString("stepName");
            int stepID = stepTable.getStep(stepName).getStepID();
            int stepInstanceID = stepInstanceTable.getStepInstance(stepID, personnelID).getStepInstanceID();
            PostRequest postRequest = postRequestsTable.getPostRequest(personnelID, studentID, stepInstanceID);

            JSONObject sendingJSONObject = new JSONObject();

            sendingJSONObject.put("question", postRequest.getQuestion());
            sendingJSONObject.put("time", postRequest.getQuestionTime());
            sendingJSONObject.put("answer", postRequest.getResponse());
            sendingJSONObject.put("requestID", postRequest.getRequestID());
            sendingJSONObject.put("studentID", postRequest.getStudentID());

            io.sendJSONObject(sendingJSONObject, response);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
