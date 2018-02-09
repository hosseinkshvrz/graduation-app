package webapp;

import appLayer.processes.ProcessInstance;
import appLayer.steps.Step;
import appLayer.steps.StepInstance;
import datalayer.tables.processes.ProcessInstanceDatabase;
import datalayer.tables.steps.StepDatabase;
import datalayer.tables.steps.StepInstanceDatabase;
import datalayer.tables.users.PostDatabase;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@WebServlet(name = "StepFinisher")
public class StepFinisher extends HttpServlet {
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private StepDatabase stepTable = new StepDatabase();
    private PostDatabase postTable = new PostDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    private ProcessInstanceDatabase processInstanceTable = new ProcessInstanceDatabase();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String endTime = dtf.format(now);
        //json must consists studentID, stepInstanceID,
        try {
            String studentID = readingJSONObject.getString("studentID");
            int stepInstanceID = readingJSONObject.getInt("stepInstanceID");
            String result = readingJSONObject.getString("result");
            stepInstanceTable.setEndTime(endTime, stepInstanceID);
            int processInstanceID = stepInstanceTable.getProcessInstanceID(stepInstanceID);
            ProcessInstance processInstance = processInstanceTable.getProcessInstance(processInstanceID);
            int currentStepID = stepInstanceTable.getStepID(stepInstanceID);
            Step nextStep;
            int nextStepID = -1;
            if (result.equals("accept")) {
                nextStepID = stepTable.getStep(currentStepID).getAcceptStepID();
            }
            else if (result.equals("reject")) {
                nextStepID = stepTable.getStep(currentStepID).getRejectStepID();
            }
            nextStep = stepTable.getStep(nextStepID);
            String personnelID = postTable.getAvailablePostID(nextStep.getDepartmentID());

            now = LocalDateTime.now();
            String startTime = dtf.format(now);

            StepInstance stepInstance = new StepInstance(nextStep.getStepID(), processInstanceID, personnelID, startTime);
            stepInstanceTable.addNewStepInstanceToDB(stepInstance);

            processInstance.addStepInstance(stepInstance);
            processInstanceTable.addStepInstancesToProcessInstance(processInstance);

            studentTable.changeCurrentState(studentID, processInstance.getID(), stepInstance.getStepInstanceID());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
