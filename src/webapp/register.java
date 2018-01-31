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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Scanner;

@WebServlet(name = "register")
public class register extends HttpServlet {
    private final int MINIMUM_BIRTH_YEAR = 1300;
    private final int MAXIMUM_BIRTH_YEAR = 1390;
    DB_Student studentTable = new DB_Student();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean hasError = false;
        String responseMessage = "";
        String json = "";
//        request.setCharacterEncoding("UTF-8");
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
        //parameter name should be checked
        //order of errors should be checked
        /*
        URLDecoder.decode(jsonObject.get("Project").toString(),"UTF-8")
        readingJSONObject.getString("email")
         */
        try {
            if(readingJSONObject.getString("email").isEmpty()) {
                responseMessage = "empty email field";
                hasError = true;
            }
            else if(!readingJSONObject.getString("email").matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                responseMessage = "invalid email format";
                hasError = true;
            }
            else if (readingJSONObject.getString("password").isEmpty()) {
                responseMessage = "empty password field";
                hasError = true;
            }
            else if (readingJSONObject.getString("confirmPassword").isEmpty()) {
                responseMessage = "empty confirm password field";
                hasError = true;
            }
            else if (!readingJSONObject.getString("password").equals(readingJSONObject.getString("confirmPassword"))) {
                responseMessage = "passwords don't match";
                hasError = true;
            }
            else if (readingJSONObject.getString("firstName").isEmpty()) {
                responseMessage = "empty first name field";
                hasError = true;
            }
            else if (!readingJSONObject.getString("firstName").matches("\"^[\\\\u0600-\\\\u06FF\\\\uFB8A\\\\u067E\\\\u0686\\\\u06AF\\\\u200C\\\\u200F ]+$\"")) {
                responseMessage = "invalid fist name format";
                hasError = true;
            }
            else if (readingJSONObject.getString("lastName").isEmpty()) {
                responseMessage = "empty last name field";
                hasError = true;
            }
            else if (!readingJSONObject.getString("lastName").matches("\"^[\\\\u0600-\\\\u06FF\\\\uFB8A\\\\u067E\\\\u0686\\\\u06AF\\\\u200C\\\\u200F ]+$\"")) {
                responseMessage = "invalid last name format";
                hasError = true;
            }
            else if (readingJSONObject.getString("studentID").isEmpty()) {
                responseMessage = "empty student id field";
                hasError = true;
            }
            else if (!readingJSONObject.getString("studentID").matches("[0-9]{8}")) {
                responseMessage = "invalid student id format";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) < MINIMUM_BIRTH_YEAR
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) > MAXIMUM_BIRTH_YEAR) {
                responseMessage = "invalid year of birth";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) < 1
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 12) {
                responseMessage = "invalid month of birth";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) < 1) {
                responseMessage = "invalid day of birth";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) == 31) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "invalid day of birth";
                    hasError = true;
                }
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) > 30) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "invalid day of birth";
                    hasError = true;
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(!hasError) {
            StudentUser student = null;
            try {
                student = new StudentUser(readingJSONObject.getString("studentID"),
                                                    readingJSONObject.getString("firstName"),
                                                    readingJSONObject.getString("lastName"),
                                                    readingJSONObject.getString("password"),
                                                    readingJSONObject.getString("email"),
                                                    Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]),
                                                    Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]),
                                                    Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            boolean userAdded = studentTable.addNewStudentToDB(student);

            if (userAdded) {
                responseMessage = "successfully registered";
            }
            else {
                responseMessage = "server is busy. try later";
            }
        }
        JSONObject sendingJSONObject = new JSONObject();
        try {
            sendingJSONObject.put("responseMessage", responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(sendingJSONObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
