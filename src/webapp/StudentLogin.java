package webapp;

import appLayer.processes.ProcessInstance;
import appLayer.steps.StepInstance;
import appLayer.users.StudentUser;
import datalayer.tables.processes.ProcessDatabase;
import datalayer.tables.processes.ProcessInstanceDatabase;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.steps.StepInstanceDatabase;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

@WebServlet(name = "StudentLogin")
public class StudentLogin extends HttpServlet {
    private StudentDatabase studentTable = new StudentDatabase();
    private ProcessInstanceDatabase processInstanceTable = new ProcessInstanceDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        JSONObject sendingJSONObject = new JSONObject();
        try {
            String studentID = readingJSONObject.getString("studentID");
            String password = readingJSONObject.getString("password");
            if (studentTable.isValidStudentLogin(studentID, password)) {
                StudentUser std = studentTable.getUser(readingJSONObject.getString("studentID"));
                int stdProcessInstanceID = std.getStartedProcessInstanceID();
                int result = 0;
                String processName = "";
                if (stdProcessInstanceID != -1) {
                    ProcessInstance processInstance = processInstanceTable.getProcessInstance(stdProcessInstanceID);
                    processName = processTable.getProcessName(processInstance.getProcessID());
                    StudentStatusChecker studentStatusChecker = new StudentStatusChecker();
                    result = studentStatusChecker.statusChecker(studentID);
                }
                sendingJSONObject.put("responseMessage", "student found");
                sendingJSONObject.put("studentID", std.getStudentID());
                sendingJSONObject.put("firstName", std.getFirstName());
                sendingJSONObject.put("lastName", std.getLastName());
                sendingJSONObject.put("status", result);
                sendingJSONObject.put("processName", processName);
                sendingJSONObject.put("email", std.getEmail());
                sendingJSONObject.put("birthDate",
                        (std.getYearOfBirth() + "-"
                                + std.getMonthOfBirth() + "-"
                                + std.getDayOfBirth()));
            }
            else {
                sendingJSONObject.put("responseMessage", "student not found");
            }
            io.sendJSONObject(sendingJSONObject, response);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
