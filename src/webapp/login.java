package webapp;

import appLayer.StudentUser;
import datalayer.DB_Student;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "login")
public class login extends HttpServlet {
    DB_Student studentTable = new DB_Student();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentUser std = null;
        try {
            std = studentTable.getStudent(request.getParameter("studentID"), request.getParameter("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("name", std.getFirstName() + " " + std.getLastName());

        try {
            if (studentTable.isValidUserLogin(request.getParameter("studentID"), request.getParameter("password"))) {
                request.getRequestDispatcher("/welcome.jsp").forward(request, response);
            }
            else {
                request.setAttribute("errorMessage", "invalid student ID or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
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
