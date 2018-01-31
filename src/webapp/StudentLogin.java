package webapp;

import appLayer.StudentUser;
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
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

@WebServlet(name = "StudentLogin")
public class StudentLogin extends HttpServlet {
    StudentDatabase studentTable = new StudentDatabase();
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
            if (studentTable.isValidStudentLogin(readingJSONObject.getString("studentID"), readingJSONObject.getString("password"))) {
                StudentUser std = studentTable.getUser(readingJSONObject.getString("studentID"));
//                request.setAttribute("name", std.getFirstName() + " " + std.getLastName());
//                request.getRequestDispatcher("/welcome.jsp").forward(request, response);
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "student found");
                sendingJSONObject.put("studentID", std.getStudentID());
                sendingJSONObject.put("firstName", std.getFirstName());
                sendingJSONObject.put("lastName", std.getLastName());
                sendingJSONObject.put("email", std.getEmail());
                sendingJSONObject.put("birthDate",
                        (std.getYearOfBirth() + "-"
                                + std.getMonthOfBirth() + "-"
                                + std.getDayOfBirth()));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sendingJSONObject.toString());
            }
            else {
//                request.setAttribute("errorMessage", "invalid student ID or password");
//                request.getRequestDispatcher("/login.jsp").forward(request, response);
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "student not found");
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
        JSONArray jsArr = new JSONArray();
        JSONObject jsObject;
        ArrayList<StudentUser> students = new ArrayList<>();
        try {
            students = studentTable.getListOfAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < students.size(); i++) {
                jsObject = new JSONObject();
                jsObject.put("studentID", students.get(i).getStudentID());
                jsObject.put("firstName", students.get(i).getFirstName());
                jsObject.put("lastName", students.get(i).getLastName());
                jsObject.put("email", students.get(i).getEmail());
                jsObject.put("birthDate",
                        (students.get(i).getYearOfBirth() + "-"
                                + students.get(i).getMonthOfBirth() + "-"
                                + students.get(i).getDayOfBirth()));
                jsArr.put(jsObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsArr.toString());
    }
}
