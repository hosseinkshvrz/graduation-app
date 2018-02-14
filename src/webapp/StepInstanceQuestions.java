package webapp;

import appLayer.PostRequest;
import appLayer.users.StudentUser;
import datalayer.tables.PostRequestsDatabase;
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


@WebServlet(name = "StepInstanceQuestions")
public class StepInstanceQuestions extends HttpServlet {
    private StudentDatabase studentTable = new StudentDatabase();
    private PostRequestsDatabase postRequestsTable = new PostRequestsDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            StudentUser student = studentTable.getUser(studentID);
            int stepInstanceID = student.getCurrentStepInstanceID();
            ArrayList<PostRequest> stepInstanceRequests = postRequestsTable.getStepInstanceRequests(stepInstanceID);
            JSONArray sendingJSONArray = new JSONArray();
            JSONObject sendingJSONObject = new JSONObject();
            for (PostRequest pr :
                    stepInstanceRequests) {
                JSONObject tempJSONObject = new JSONObject();
                String questionResponse = pr.getResponse();
                if (questionResponse == null) {
                    questionResponse = "پاسخ داده نشده‌است";
                }
                tempJSONObject.put("question", pr.getQuestion());
                tempJSONObject.put("answer", questionResponse);
                tempJSONObject.put("date", pr.getQuestionTime());
                tempJSONObject.put("studentID", pr.getStudentID());
                sendingJSONArray.put(tempJSONObject);
            }
            sendingJSONObject.put("responseMessage", "success");
            sendingJSONObject.put("requests", sendingJSONArray);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
