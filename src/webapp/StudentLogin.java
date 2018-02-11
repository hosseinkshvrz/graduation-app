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
        try {
            if (studentTable.isValidStudentLogin(readingJSONObject.getString("studentID"), readingJSONObject.getString("password"))) {
                StudentUser std = studentTable.getUser(readingJSONObject.getString("studentID"));
//                request.setAttribute("name", std.getFirstName() + " " + std.getLastName());
//                request.getRequestDispatcher("/welcome.jsp").forward(request, response);
                JSONObject sendingJSONObject = new JSONObject();
                ProcessInstance processInstance = processInstanceTable.getProcessInstance(std.getStartedProcessInstanceID());
                String processName = processTable.getProcessName(processInstance.getProcessID());
                sendingJSONObject.put("responseMessage", "student found");
                sendingJSONObject.put("studentID", std.getStudentID());
                sendingJSONObject.put("firstName", std.getFirstName());
                sendingJSONObject.put("lastName", std.getLastName());
                sendingJSONObject.put("processName", processName);
                sendingJSONObject.put("email", std.getEmail());
                sendingJSONObject.put("birthDate",
                        (std.getYearOfBirth() + "-"
                                + std.getMonthOfBirth() + "-"
                                + std.getDayOfBirth()));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(sendingJSONObject.toString());
            }
            else {
//                request.setAttribute("errorMessage", "invalid student ID or password");
//                request.getRequestDispatcher("/login.jsp").forward(request, response);
                JSONObject sendingJSONObject = new JSONObject();
                sendingJSONObject.put("responseMessage", "student not found");
                io.sendJSONObject(sendingJSONObject, response);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
