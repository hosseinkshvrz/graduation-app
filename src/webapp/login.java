package webapp;

import appLayer.StudentUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "login")
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentUser studentUser = new StudentUser();
        request.setAttribute("name", studentUser.getName(request.getParameter("studentID"), request.getParameter("password")));

        try {
            if (studentUser.isValidUser(request.getParameter("studentID"), request.getParameter("password"))) {
                request.getRequestDispatcher("/welcome.jsp").forward(request, response);
            }
            else {
                request.setAttribute("errorMessage", "invalid student ID or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
