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
        boolean hasError = false;

        if(!request.getParameter("email").matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")) {
            request.setAttribute("errorMessage1", "*Incorrect email format.");
            hasError = true;
        }
        if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
            request.setAttribute("errorMessage2", "*Passwords don't match.");
            hasError = true;
        }
        if (!request.getParameter("name").matches("[A-Za-z ]*")) {
            request.setAttribute("errorMessage3", "*Name is incorrect.");
            hasError = true;
        }
        if (!request.getParameter("studentID").matches("[0-9]{8}")) {
            request.setAttribute("errorMessage4", "*StudentID is incorrect.");
            hasError = true;
        }

        User user = new User();
        boolean userAdded = user.addStudent(request.getParameter("studentID"),
                request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        if(hasError) {
            if (userAdded) {
                request.setAttribute("successfulRegister", "You have successfully registered");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Register was unsuccessful");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
