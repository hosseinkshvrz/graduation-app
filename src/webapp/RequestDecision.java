package webapp;

import appLayer.processes.Process;
import appLayer.processes.ProcessInstance;
import appLayer.steps.Step;
import appLayer.steps.StepInstance;
import datalayer.tables.ProcessRequestsDatabase;
import datalayer.tables.processes.ProcessDatabase;
import datalayer.tables.processes.ProcessInstanceDatabase;
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


@WebServlet(name = "RequestDecision")
public class RequestDecision extends HttpServlet {
    private ProcessInstanceDatabase processInstanceTable = new ProcessInstanceDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private PostDatabase postTable = new PostDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    private ProcessRequestsDatabase requestsTable = new ProcessRequestsDatabase();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
//        String responseMessage;
        try {
            Process process = processTable.getProcess(readingJSONObject.getString("processName"));
            String studentID = readingJSONObject.getString("studentID");
            String result = readingJSONObject.getString("result");
            requestsTable.changeStatus(studentID, process.getID(), result);
            if (result.equals("accept")) {
                ProcessInstance processInstance = new ProcessInstance(process.getID());
                processInstanceTable.addNewProcessInstanceToDB(processInstance);

                Step firstStep = process.getFirstStep();

                int stepID = firstStep.getStepID();
                int pInstanceID = processInstance.getID();

                String personnelID = postTable.getAvailablePostID(firstStep.getDepartmentID());


                String startTime = Date.getCurrentTimeAndDate();
                String stepResult = "stall";
                StepInstance stepInstance = new StepInstance(stepID, pInstanceID, personnelID, startTime, studentID, stepResult);
                stepInstanceTable.addNewStepInstanceToDB(stepInstance);

                processInstance.addStepInstance(stepInstance);
                processInstanceTable.addStepInstancesToProcessInstance(processInstance);

                studentTable.changeCurrentState(studentID, processInstance.getID(), stepInstance.getStepInstanceID());
            }
            else {

            }
//            JSONObject sendingJSONObject = new JSONObject();
//            responseMessage = "فرآیند با موفقیت آغاز شد";
//            sendingJSONObject.put("stepInstanceID", stepInstance.getStepInstanceID());
//            sendingJSONObject.put("studentID", studentID);
//            sendingJSONObject.put("responseMessage", responseMessage);
//            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
