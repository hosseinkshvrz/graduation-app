package webapp;

import appLayer.ProcessRequest;
import datalayer.tables.ProcessRequestsDatabase;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hossein on 12/02/2018.
 */
public class StudentStatusChecker {

    public int statusChecker(String studentID) {
        ProcessRequestsDatabase requestsTable = new ProcessRequestsDatabase();
        ArrayList<ProcessRequest> studentRequests = requestsTable.getStudentRequests(studentID);
        ProcessRequest selectedRequest = null;
        for (ProcessRequest pr :
                studentRequests) {
            if (pr.getStatus().equals("yes")) {
                selectedRequest = pr;
                break;
            }
            else if (pr.getStatus().equals("stall")) {
                selectedRequest = pr;
            }
        }
        int result = 0;
        if (studentRequests.size() == 0) {
            result = 0;
        }
        else if (selectedRequest == null) {
            result = 2;
        }
        else if (selectedRequest.getStatus().equals("stall")) {
            result = 1;
        }
        else if (selectedRequest.getStatus().equals("yes")) {
            result = 3;
        }
        return result;
    }
}
