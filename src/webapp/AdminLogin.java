package webapp;

import appLayer.users.AdminUser;
import datalayer.tables.users.AdminDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "AdminLogin")
public class AdminLogin extends HttpServlet {
    AdminDatabase adminTable = new AdminDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            JSONObject sendingJSONObject = new JSONObject();
            if (adminTable.isValidAdminLogin(readingJSONObject.getString("id"), readingJSONObject.getString("password"))) {
                AdminUser admin = adminTable.getUser(readingJSONObject.getString("id"));
                sendingJSONObject.put("responseMessage", "success");
                sendingJSONObject.put("id", admin.getId());
                sendingJSONObject.put("firstName", admin.getFirstName());
                sendingJSONObject.put("lastName", admin.getLastName());
                sendingJSONObject.put("email", admin.getEmail());
            }
            else {
                sendingJSONObject.put("responseMessage", "failed");
            }
            io.sendJSONObject(sendingJSONObject, response);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
