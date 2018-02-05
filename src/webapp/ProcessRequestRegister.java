package webapp;

import appLayer.processes.Process;
import datalayer.tables.*;
import datalayer.tables.processes.ProcessDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ProcessRequestRegister")
public class ProcessRequestRegister extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    private ProcessRequestsDatabase processRequestsTable = new ProcessRequestsDatabase();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONString(request);

        String processName = "";
        String studentID = "";
        try {
            processName = readingJSONObject.getString("processName");
            studentID = readingJSONObject.getString("studentID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Process process = processTable.getProcess(processName);
        String responseMessage;
        boolean notDuplicateRequest = processRequestsTable.addRequest(studentID, process.getID());
        if (notDuplicateRequest) {
            responseMessage = "درخواست شما با موفقیت در سیستم ثبت شد. تا تایید مسئول مربوط منتظر بمانید";
        }
        else {
            responseMessage = "شما در حال حاضر یک درخواست در سامانه ثبت کرده‌اید";
        }
        JSONObject sendingJSONObject = new JSONObject();
        try {
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
