package webapp;

import appLayer.users.StudentUser;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@WebServlet(name = "StudentRegister")
public class StudentRegister extends HttpServlet {
    StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        ProfileFieldChecker fieldChecker = new ProfileFieldChecker();
        String responseMessage = fieldChecker.checkFields(readingJSONObject);
        boolean hasError = true;
        if (responseMessage.equals("")) {
            hasError = false;
        }

        if(!hasError) {
            StudentUser student;
            try {
                student = new StudentUser(readingJSONObject.getString("studentID"),
                                            readingJSONObject.getString("firstName"),
                                            readingJSONObject.getString("lastName"),
                                            readingJSONObject.getString("password"),
                                            readingJSONObject.getString("email"),
                                            Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]),
                                            Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]),
                                            Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]));
                student.setCurrentStepInstanceID(-1);
                student.setStartedProcessInstanceID(-1);
                boolean userAdded = studentTable.addNewStudentToDB(student);
                if (userAdded) {
                    responseMessage = "ثبت نام با موفقیت انجام شد";
                }
                else {
                    responseMessage = "این شماره دانشجویی از قبل در سامانه ثبت شده‌است";
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject sendingJSONObject = new JSONObject();
        try {
            sendingJSONObject.put("responseMessage", responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        io.sendJSONObject(sendingJSONObject, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
