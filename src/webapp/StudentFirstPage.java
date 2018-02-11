package webapp;

import appLayer.ProcessRequest;
import datalayer.tables.ProcessRequestsDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "StudentFirstPage")
public class StudentFirstPage extends HttpServlet {
    private ProcessRequestsDatabase requestsTable = new ProcessRequestsDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
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
            String responseMessage = null;
            JSONObject sendingJSONObject = new JSONObject();
            if (selectedRequest == null && studentRequests.size() == 0) {
                responseMessage = "شما تا به حال فرآیندی را برای شروع انتخاب نکرده‌اید. " +
                        "\nلطفاً از لیست فرآیند‌ها یکی را برای شروع انتخاب کنید";
                result = 0;
            }
            else if (selectedRequest.getStatus().equals("stall")) {
                responseMessage = "درخواست شروع فرآیند شما توسط مسئول مربوط در حال بررسی است." +
                        "\nلطفاً تا زمان تصمیم‌گیری راجع به درخواستتان صبور باشد";
                result = 1;
            }
            else if (selectedRequest == null && studentRequests.size() != 0) {
                responseMessage = "آخرین درخواست شروع فرآیند شما توسط مسئول مربوط رد شده‌است." +
                        "\nلطفاً از لیست فرآیند‌ها یکی را برای شروع انتخاب کنید";
                result = 2;
            }
            else if (selectedRequest.getStatus().equals("yes")) {
                responseMessage = "درخواست شروع فرآیند شما توسط مسئول مربوط تایید گردیده‌است.";
                result = 3;
            }
            sendingJSONObject.put("status", result);
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
