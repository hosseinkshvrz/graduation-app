package webapp;

import appLayer.PostRequest;
import appLayer.users.PostUser;
import datalayer.tables.PostRequestsDatabase;
import datalayer.tables.users.PostDatabase;
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

@WebServlet(name = "StudentQuestions")
public class StudentQuestions extends HttpServlet {
    private PostRequestsDatabase postRequestTable = new PostRequestsDatabase();
    private PostDatabase postTable = new PostDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            ArrayList<PostRequest> allRequests = postRequestTable.getAllStudentRequests(studentID);
            JSONObject sendingJSONObject = new JSONObject();
            JSONArray sendingJSONArray = new JSONArray();
            for (PostRequest pr :
                    allRequests) {
                if (pr.getResponse() == null) {
                    JSONObject tempJSONObject = new JSONObject();
                    PostUser post = postTable.getUser(pr.getPersonnelID());
                    tempJSONObject.put("question", pr.getQuestion());
                    tempJSONObject.put("departmentID", post.getDepartmentID());
                    tempJSONObject.put("date", pr.getQuestionTime());
                    tempJSONObject.put("requestID", pr.getRequestID());
                    sendingJSONArray.put(tempJSONObject);
                }
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
