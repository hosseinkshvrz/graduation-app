package webapp;

import appLayer.StudentUser;
import com.google.gson.Gson;
import datalayer.DB_Student;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "register")
public class register extends HttpServlet {
    private final int MINIMUM_BIRTH_YEAR = 1300;
    private final int MAXIMUM_BIRTH_YEAR = 1390;
    DB_Student studentTable = new DB_Student();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasError = false;
        String responseMessage = "";

        //parameter name should be checked
        //order of errors should be checked

        if(request.getParameter("email").isEmpty()) {
            responseMessage = "empty email field";
            hasError = true;
        }
        else if(!request.getParameter("email").matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            responseMessage = "invalid email format";
            hasError = true;
        }
        else if (!request.getParameter("password").isEmpty()) {
            responseMessage = "empty password field";
            hasError = true;
        }
        else if (request.getParameter("confirmPassword").isEmpty()) {
            responseMessage = "empty confirm password field";
            hasError = true;
        }
        else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
            responseMessage = "passwords don't match";
            hasError = true;
        }
        else if (request.getParameter("firstName").isEmpty()) {
            responseMessage = "empty first name field";
            hasError = true;
        }
        else if (!request.getParameter("firstName").matches("[A-Za-z ]*")) {
            responseMessage = "invalid fist name format";
            hasError = true;
        }
        else if (request.getParameter("lastName").isEmpty()) {
            responseMessage = "empty last name field";
            hasError = true;
        }
        else if (!request.getParameter("lastName").matches("[A-Za-z ]*")) {
            responseMessage = "invalid last name format";
            hasError = true;
        }
        else if (request.getParameter("studentID").isEmpty()) {
            responseMessage = "empty student id field";
            hasError = true;
        }
        else if (!request.getParameter("studentID").matches("[0-9]{8}")) {
            responseMessage = "invalid student id format";
            hasError = true;
        }
        else if (Integer.parseInt(request.getParameter("birthDate").split("-")[0]) < MINIMUM_BIRTH_YEAR
                || Integer.parseInt(request.getParameter("birthDate").split("-")[0]) > MAXIMUM_BIRTH_YEAR) {
            responseMessage = "invalid year of birth";
            hasError = true;
        }
        else if (Integer.parseInt(request.getParameter("birthDate").split("-")[1]) < 1
                || Integer.parseInt(request.getParameter("birthDate").split("-")[1]) > 12) {
            responseMessage = "invalid month of birth";
            hasError = true;
        }
        else if (Integer.parseInt(request.getParameter("birthDate").split("-")[3]) < 1) {
            responseMessage = "invalid day of birth";
            hasError = true;
        }
        else if (Integer.parseInt(request.getParameter("birthDate").split("-")[3]) == 31) {
            if (Integer.parseInt(request.getParameter("birthDate").split("-")[1]) > 6) {
                responseMessage = "invalid day of birth";
                hasError = true;
            }
        }
        else if (Integer.parseInt(request.getParameter("birthDate").split("-")[3]) > 30) {
            if (Integer.parseInt(request.getParameter("birthDate").split("-")[1]) > 6) {
                responseMessage = "invalid day of birth";
                hasError = true;
            }
        }

        if(!hasError) {
            StudentUser student = new StudentUser(request.getParameter("studentID"),
                                                request.getParameter("password"),
                                                request.getParameter("firstname"),
                                                request.getParameter("lastname"),
                                                request.getParameter("email"),
                                                Integer.parseInt(request.getParameter("birthDate").split("-")[2]),
                                                Integer.parseInt(request.getParameter("birthDate").split("-")[1]),
                                                Integer.parseInt(request.getParameter("birthDate").split("-")[0]));

            boolean userAdded = studentTable.addNewStudentToDB(student);

            if (userAdded) {
                responseMessage = "successfully registered";
            }
            else {
                responseMessage = "server is busy. try later";
            }
        }
        JSONObject jsObject = new JSONObject();
        try {
            jsObject.put("responseMessage", responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONArray().put(jsObject).toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
