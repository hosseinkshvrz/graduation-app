package webapp;

import appLayer.Transaction;
import datalayer.tables.TransactionsDatabase;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            String departmentID = readingJSONObject.getString("departmentID");
            int stepInstanceID = readingJSONObject.getInt("stepInstanceID");
            int amount = readingJSONObject.getInt("amount");
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
