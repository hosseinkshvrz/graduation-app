package webapp;

import appLayer.users.StudentUser;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StudentEdition extends HttpServlet {

    StudentDatabase studentTable = new StudentDatabase();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);

        try {
            String studentID = readingJSONObject.getString("studentID");
            String birthDate = readingJSONObject.getString("birthDate");
//            if (birthDate.equals("null-null-null")) {
//                birthDate = studentTable.getUser(studentID).getBirthDate();
//            }
            ProfileFieldChecker fieldChecker = new ProfileFieldChecker();
            String responseMessage = fieldChecker.checkFields(readingJSONObject);
            boolean hasError = true;
            if (responseMessage.equals("")) {
                hasError = false;
            }
            if(!hasError) {
                StudentUser student = new StudentUser(readingJSONObject.getString("firstName"),
                        readingJSONObject.getString("lastName"),
                        readingJSONObject.getString("password"),
                        readingJSONObject.getString("email"),
                        studentID,
                        Integer.parseInt(birthDate.split("-")[2]),
                        Integer.parseInt(birthDate.split("-")[1]),
                        Integer.parseInt(birthDate.split("-")[0]));

                studentTable.editStudent(student);
                responseMessage = "ویرایش با موفقیت انجام شد";
            }
            JSONObject sendingJSONObject = new JSONObject();
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
