package webapp;

import appLayer.ProcessRequest;
import datalayer.tables.ProcessRequestsDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "StudentFirstPage")
public class StudentFirstPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            StudentStatusChecker studentStatusChecker = new StudentStatusChecker();
            int result = studentStatusChecker.statusChecker(studentID);
            JSONObject sendingJSONObject = new JSONObject();
            sendingJSONObject.put("status", result);
            sendingJSONObject.put("responseMessage", "success");
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
