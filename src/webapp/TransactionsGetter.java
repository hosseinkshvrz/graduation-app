package webapp;

import appLayer.Transaction;
import datalayer.tables.TransactionsDatabase;
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


@WebServlet(name = "TransactionsGetter")
public class TransactionsGetter extends HttpServlet {
    private TransactionsDatabase TransactionTable = new TransactionsDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray sendingJsonArray = new JSONArray();
        ArrayList<Transaction> allTransactions = TransactionTable.getTransactions();
        for (int i = 0; i < allTransactions.size(); i++) {
            JSONObject transactionJSONObject = new JSONObject();
            Transaction transaction = allTransactions.get(i);
            try {
                transactionJSONObject.put("transactionID", transaction.getID());
                transactionJSONObject.put("studentID", transaction.getStudentID());
                transactionJSONObject.put("departmentID", transaction.getDepartmentID());
                transactionJSONObject.put("stepInstanceID", transaction.getStepInstanceID());
                transactionJSONObject.put("amount", transaction.getAmount());
                sendingJsonArray.put(transactionJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        InputOutputHandler io = new InputOutputHandler();
        io.sendJSONArray(sendingJsonArray, response);
    }
}
