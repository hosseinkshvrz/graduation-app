package webapp;

import appLayer.users.PostUser;
import datalayer.tables.users.PostDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "PostLogin")
public class PostLogin extends HttpServlet {
    PostDatabase postTable = new PostDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            if (postTable.isValidPostLogin(readingJSONObject.getString("personnelID"), readingJSONObject.getString("password"))) {
                PostUser post = postTable.getUser(readingJSONObject.getString("personnelID"));
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "success");
                sendingJSONObject.put("personnelID", post.getPersonnelID());
                sendingJSONObject.put("firstName", post.getFirstName());
                sendingJSONObject.put("lastName", post.getLastName());
                sendingJSONObject.put("email", post.getEmail());
                sendingJSONObject.put("departmentID", post.getDepartmentID());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sendingJSONObject.toString());
            }
            else {
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "fail");
                io.sendJSONObject(sendingJSONObject, response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
