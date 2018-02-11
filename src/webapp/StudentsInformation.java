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
        JSONArray sendingJSONArray = new JSONArray();
        ArrayList<StudentUser> students = new ArrayList<>();
        try {
            students = studentTable.getListOfAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < students.size(); i++) {
                JSONObject sendingJSONObject = new JSONObject();
                String processName = processTable.getProcessName(students.get(i).getStartedProcessInstanceID());
                String stepName = stepTable.getStep(students.get(i).getCurrentStepInstanceID()).getStepName();
                sendingJSONObject.put("studentID", students.get(i).getStudentID());
                sendingJSONObject.put("firstName", students.get(i).getFirstName());
                sendingJSONObject.put("lastName", students.get(i).getLastName());
                sendingJSONObject.put("email", students.get(i).getEmail());
                sendingJSONObject.put("processName", processName);
                sendingJSONObject.put("stepName", stepName);
                sendingJSONObject.put("birthDate",
                        (students.get(i).getYearOfBirth() + "-"
                                + students.get(i).getMonthOfBirth() + "-"
                                + students.get(i).getDayOfBirth()));
                sendingJSONArray.put(sendingJSONObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputOutputHandler io = new InputOutputHandler();
        io.sendJSONArray(sendingJSONArray, response);
    }
}
