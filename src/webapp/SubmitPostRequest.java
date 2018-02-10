package webapp;

import appLayer.PostRequest;
import appLayer.users.StudentUser;
import datalayer.tables.PostRequestsDatabase;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "SubmitPostRequest")
public class SubmitPostRequest extends HttpServlet {
    private PostRequestsDatabase postRequestsTable = new PostRequestsDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String personnelID = readingJSONObject.getString("personnelID");
            String studentID = readingJSONObject.getString("studentID");
            String question = readingJSONObject.getString("question");
            StudentUser student = studentTable.getUser(studentID);
            int stepInstanceID = student.getCurrentStepInstanceID();
            String time = Date.getCurrentTimeAndDate();
            PostRequest postRequest = new PostRequest(question, personnelID, stepInstanceID, studentID, time);
            postRequestsTable.addNewRequest(postRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
