package webapp;

import appLayer.Process;
import appLayer.ProcessInstance;
import appLayer.Step;
import appLayer.StepInstance;
import datalayer.tables.ProcessDatabase;
import datalayer.tables.ProcessInstanceDatabase;
import datalayer.tables.StepInstanceDatabase;
import datalayer.tables.users.PostDatabase;
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


@WebServlet(name = "ProcessStarter")
public class ProcessStarter extends HttpServlet {
    private ProcessInstanceDatabase processInstanceTable = new ProcessInstanceDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    private StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();
    private PostDatabase postTable = new PostDatabase();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONString(request);
        Process process = null;
        try {
            process = processTable.getProcess(readingJSONObject.getString("processName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ProcessInstance processInstance = new ProcessInstance(process.getProcessID());
        processInstanceTable.addNewProcessInstanceToDB(processInstance);
        Step firstStep = process.getFirstStep();
        int stepID = firstStep.getStepID();
        int pInstanceID = processInstance.getProcessInstanceID();
        String personnelID = postTable.getAvailablePostID();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startTime = dtf.format(now);
        StepInstance stepInstance = new StepInstance(stepID, pInstanceID, personnelID, startTime);
        stepInstanceTable.addNewStepInstanceToDB(stepInstance);
        processInstance.addStepInstance(stepInstance);
        processInstanceTable.addSingleStepInstanceToProcessInstance(processInstance); //what?
        String responseMessage = "فرآیند با موفقیت آغاز شد";
        //send step instance id to android device and name
        JSONObject sendingJSONObject = new JSONObject();
        try {
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
