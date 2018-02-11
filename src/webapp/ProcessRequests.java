package webapp;

import appLayer.ProcessRequest;
import datalayer.tables.ProcessRequestsDatabase;
import datalayer.tables.processes.ProcessDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ProcessRequests")
public class ProcessRequests extends HttpServlet {
    private ProcessRequestsDatabase requestsTable = new ProcessRequestsDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    //this servlets show all student process requests to admin and admin can accept or reject students requests.
    //after decision admin make android send request to url processStarter
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray sendingJsonArray = new JSONArray();
        ArrayList<ProcessRequest> allRequests = requestsTable.getRequests();
        for (int i = 0; i < allRequests.size(); i++) {
            JSONObject requestJSONObject = new JSONObject();
            int processID = allRequests.get(i).getProcessID();
            String processName = processTable.getProcessName(processID);
            String studentID = allRequests.get(i).getStudentID();
            try {
                requestJSONObject.put("processName", processName);
                requestJSONObject.put("studentID", studentID);
                sendingJsonArray.put(requestJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        InputOutputHandler io = new InputOutputHandler();
        io.sendJSONArray(sendingJsonArray, response);
    }
}
