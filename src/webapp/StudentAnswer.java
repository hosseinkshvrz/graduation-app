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

@WebServlet(name = "StudentAnswer")
public class StudentAnswer extends HttpServlet {
    private PostRequestsDatabase postRequestsTable = new PostRequestsDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            int requestID = readingJSONObject.getInt("requestID");
            String answer = readingJSONObject.getString("answer");
            postRequestsTable.setResponse(requestID, answer);
            String responseMessgae = "پاسخ شما با موفقیت ثبت شد. تا بررسی پاسخ شما توسط اداره مربوط منتظر بمانید";
            JSONObject sendingJSONObject = new JSONObject();
            sendingJSONObject.put("responseMessage", responseMessgae);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
