package webapp;

import appLayer.Debt;
import appLayer.users.PostUser;
import datalayer.tables.DebtDatabase;
import datalayer.tables.users.PostDatabase;
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


@WebServlet(name = "StudentTransactions")
public class StudentTransactions extends HttpServlet {
    private DebtDatabase debtTable = new DebtDatabase();
    private PostDatabase postTable = new PostDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputOutputHandler io = new InputOutputHandler();
        JSONObject readingJSONObject = io.getJSONObject(request);
        try {
            String studentID = readingJSONObject.getString("studentID");
            ArrayList<Debt> allDebts = debtTable.getAllStudentDebts(studentID);
            JSONArray sendingJSONArray = new JSONArray();
            JSONObject sendingJSONObject = new JSONObject();
            for (Debt d :
                    allDebts) {
                if (d.getStatus().equals("paid")) {
                    JSONObject tempJSONObject = new JSONObject();
                    PostUser post = postTable.getUser(d.getPersonnelID());
                    tempJSONObject.put("amount", d.getAmount());
                    tempJSONObject.put("departmentID", post.getDepartmentID());
                    tempJSONObject.put("date", d.getPaymentTime());
                    tempJSONObject.put("reason", d.getDescription());
                    sendingJSONArray.put(tempJSONObject);
                }
            }
            sendingJSONObject.put("responseMessage", "success");
            sendingJSONObject.put("transactions", sendingJSONArray);
            io.sendJSONObject(sendingJSONObject, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
