package webapp;

import appLayer.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Hossein on 27/01/2018.
 */
@WebServlet(name = "register")
public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        boolean userAdded = user.addStudent(request.getParameter("studentID"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        if (request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
            if (userAdded) {
                request.setAttribute("successfulRegister", "You have successfully registered");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Register was unsuccessful");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
        else {
            request.setAttribute("errorMessage", "passwords don't match");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
