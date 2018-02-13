package webapp;

import appLayer.users.StudentUser;
import datalayer.tables.processes.ProcessDatabase;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.users.StudentDatabase;
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


@WebServlet(name = "StudentsInformation")
public class StudentsInformation extends HttpServlet {
    private StudentDatabase studentTable = new StudentDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepDatabase stepTable = new StepDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<StudentUser> students = new ArrayList<>();
        try {
            students = studentTable.getListOfAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            JSONArray sendingJSONArray = new JSONArray();
            JSONObject sendingJSONObject = new JSONObject();
            for (int i = 0; i < students.size(); i++) {
                JSONObject tempJSONObject = new JSONObject();
                String processName = processTable.getProcessName(students.get(i).getStartedProcessInstanceID());
                String stepName = stepTable.getStep(students.get(i).getCurrentStepInstanceID()).getStepName();
                tempJSONObject.put("studentID", students.get(i).getStudentID());
                tempJSONObject.put("firstName", students.get(i).getFirstName());
                tempJSONObject.put("lastName", students.get(i).getLastName());
                tempJSONObject.put("email", students.get(i).getEmail());
                tempJSONObject.put("processName", processName);
                tempJSONObject.put("stepName", stepName);
                tempJSONObject.put("birthDate",
                        (students.get(i).getYearOfBirth() + "-"
                                + students.get(i).getMonthOfBirth() + "-"
                                + students.get(i).getDayOfBirth()));
                sendingJSONArray.put(tempJSONObject);
            }
            sendingJSONObject.put("responseMessage", "success");
            sendingJSONObject.put("students", sendingJSONArray);
            InputOutputHandler io = new InputOutputHandler();
            io.sendJSONObject(sendingJSONObject, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
