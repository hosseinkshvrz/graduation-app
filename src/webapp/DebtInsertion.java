package webapp;

import appLayer.Debt;
import appLayer.users.StudentUser;
import datalayer.tables.DebtDatabase;
import datalayer.tables.users.StudentDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DebtInsertion")
public class DebtInsertion extends HttpServlet {
    private DebtDatabase debtTable = new DebtDatabase();
    private StudentDatabase studentTable = new StudentDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String personnelID = readingJSONObject.getString("personnelID");
            String studentID = readingJSONObject.getString("studentID");
            int amount = readingJSONObject.getInt("amount");
            String time = Date.getCurrentTimeAndDate();
            String description = readingJSONObject.getString("reason");
            StudentUser student = studentTable.getUser(studentID);
            int stepInstanceID = student.getCurrentStepInstanceID();
            String status = "wait";
            Debt debt = new Debt(studentID, amount, personnelID, stepInstanceID, status, time, description);
            debtTable.addNewDebt(debt);
            JSONObject sendingJSONObject = new JSONObject();
            String responseMessage = "success";
            sendingJSONObject.put("responseMessage", responseMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
