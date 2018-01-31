package webapp;

import appLayer.AdminUser;
import datalayer.tables.users.AdminDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;


@WebServlet(name = "AdminLogin")
public class AdminLogin extends HttpServlet {
    AdminDatabase adminTable = new AdminDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = "";
        Scanner scanner = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        System.out.println(json);
        JSONObject readingJSONObject = new JSONObject();
        try {
            readingJSONObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (adminTable.isValidAdminLogin(readingJSONObject.getString("id"), readingJSONObject.getString("password"))) {
                AdminUser admin = adminTable.getUser(readingJSONObject.getString("id"));
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "success");
                sendingJSONObject.put("id", admin.getId());
                sendingJSONObject.put("firstName", admin.getFirstName());
                sendingJSONObject.put("lastName", admin.getLastName());
                sendingJSONObject.put("email", admin.getEmail());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sendingJSONObject.toString());
            }
            else {
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "failed");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sendingJSONObject.toString());
            }
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
