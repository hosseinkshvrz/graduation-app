package webapp;

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
import java.util.HashMap;


@WebServlet(name = "StudentRequestsGetter")
public class StudentRequestsGetter extends HttpServlet {
    private ProcessRequestsDatabase requestsTable = new ProcessRequestsDatabase();
    private ProcessDatabase processTable = new ProcessDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    //this servlets show all student process requests to admin and admin can accept or reject students requests.
    //after decision admin make android send request to url processStarter
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray sendingJsonArray = new JSONArray();
        HashMap<String, Integer> allRequests = requestsTable.getRequests();
        for (String key :
                allRequests.keySet()) {
            JSONObject requestJSONObject = new JSONObject();
            int processID = allRequests.get(key);
            String processName = processTable.getProcessName(processID);
            try {
                requestJSONObject.put("processName", processName);
                requestJSONObject.put("studentID", key);
                sendingJsonArray.put(requestJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        InputOutputHandler io = new InputOutputHandler();
        io.sendJSONArray(sendingJsonArray, response);
    }
}
