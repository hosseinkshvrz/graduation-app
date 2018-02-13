package webapp;

import appLayer.Debt;
import appLayer.Transaction;
import datalayer.tables.DebtDatabase;
import datalayer.tables.TransactionsDatabase;
import datalayer.tables.users.PostDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Payment")
public class Payment extends HttpServlet {
    private TransactionsDatabase transactionsTable = new TransactionsDatabase();
    private DebtDatabase debtTable = new DebtDatabase();
    private PostDatabase postTable = new PostDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            int debtID = readingJSONObject.getInt("debtID");
            String time = Date.getCurrentTimeAndDate();
            debtTable.changeStatus(debtID, time);
            Debt debt = debtTable.getDebt(debtID);
            String studentID = debt.getStudentID();
            String departmentID = postTable.getUser(debt.getPersonnelID()).getDepartmentID();
            int stepInstanceID = debt.getStepInstanceID();
            int amount = debt.getAmount();
            Transaction transaction = new Transaction(studentID, departmentID, stepInstanceID, amount);
            transactionsTable.addNewTransaction(transaction);
            String responseMessage = "success";
            JSONObject sendingJSONObject = new JSONObject();
            sendingJSONObject.put("responseMessage", responseMessage);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
