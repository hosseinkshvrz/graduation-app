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
    private final int MINIMUM_BIRTH_YEAR = 1300;
    private final int MAXIMUM_BIRTH_YEAR = 1390;
    StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        String responseMessage = "";
        boolean hasError = false;
        try {
            if (readingJSONObject.getString("firstName").isEmpty()) {
                responseMessage = "empty first name field";
                hasError = true;
            }
            else if (readingJSONObject.getString("firstName").matches("\"^[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF\\u200C\\u200F ]+$\"")) {
                responseMessage = "نام باید به حروف فارسی باشد";
                hasError = true;
            }
            else if (readingJSONObject.getString("lastName").isEmpty()) {
                responseMessage = "empty last name field";
                hasError = true;
            }
            else if (readingJSONObject.getString("lastName").matches("\"^[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF\\u200C\\u200F ]+$\"")) {
                responseMessage = "نام خانوادگی باید به حروف فارسی باشد";
                hasError = true;
            }
            else if (readingJSONObject.getString("studentID").isEmpty()) {
                responseMessage = "empty student id field";
                hasError = true;
            }
            else if (!readingJSONObject.getString("studentID").matches("[0-9]{8}")) {
                responseMessage = "شماره دانشجویی وارد شده صحیح نیست";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) < MINIMUM_BIRTH_YEAR
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]) > MAXIMUM_BIRTH_YEAR) {
                responseMessage = "سال تولد نامعتبر است";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) < 1
                    || Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 12) {
                responseMessage = "ماه تولد نامعتبر است";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) < 1) {
                responseMessage = "روز تولد نامعتبر است";
                hasError = true;
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) == 31) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "روز تولد نامعتبر است";
                    hasError = true;
                }
            }
            else if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[2]) > 30) {
                if (Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[1]) > 6) {
                    responseMessage = "روز تولد نامعتبر است";
                    hasError = true;
                }
            }
            else if(readingJSONObject.getString("email").isEmpty()) {
                responseMessage = "empty email field";
                hasError = true;
            }
            else if(!readingJSONObject.getString("email").matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                responseMessage = "فرمت ایمیل صحیح نیست";
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
                responseMessage = "کلمه عبور باید عیناً تکرار شود";
                hasError = true;
            }
            else if (readingJSONObject.getString("password").length() < 8 || readingJSONObject.getString("password").length() > 22) {
                responseMessage = "طول کلمه عبور باید حداقل ۸ و حداکثر ۲۲ حرف باشد";
                hasError = true;
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
                                            Integer.parseInt(readingJSONObject.getString("birthDate").split("-")[0]),
                                            readingJSONObject.getString("status"));

                boolean userAdded = studentTable.addNewStudentToDB(student);
                if (userAdded) {
                    responseMessage = "ثبت نام با موفقیت انجام شد";
                }
                else {
                    responseMessage = "این شماره دانشجویی از قبل در سامانه ثبت شده‌است";
                }
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", responseMessage);
                io.sendJSONObject(sendingJSONObject, response);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
