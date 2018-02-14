package webapp;

import appLayer.Debt;
import appLayer.users.StudentUser;
import datalayer.tables.DebtDatabase;
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
import java.util.ArrayList;


@WebServlet(name = "StepInstanceDebts")
public class StepInstanceDebts extends HttpServlet {
    private StudentDatabase studentTable = new StudentDatabase();
    private DebtDatabase debtTable = new DebtDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            StudentUser student = studentTable.getUser(studentID);
            int stepInstanceID = student.getCurrentStepInstanceID();
            ArrayList<Debt> stepInstanceDebts = debtTable.getStepInstanceDebts(stepInstanceID);
            JSONArray sendingJSONArray = new JSONArray();
            JSONObject sendingJSONObject = new JSONObject();
            for (Debt d :
                    stepInstanceDebts) {
                String status = d.getStatus();
                if (status.equals("paid")) {
                    status = "پرداخت شده";
                }
                else {
                    status = "پرداخت نشده";
                }
                JSONObject tempJSONObject = new JSONObject();
                tempJSONObject.put("amount", d.getAmount());
                tempJSONObject.put("status", status);
                tempJSONObject.put("date", d.getDebtTime());
                tempJSONObject.put("reason", d.getDescription());
                sendingJSONArray.put(tempJSONObject);
            }
            sendingJSONObject.put("responseMessage", "success");
            sendingJSONObject.put("debts", sendingJSONArray);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
