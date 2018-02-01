package webapp;

import appLayer.Process;
import appLayer.Step;
import datalayer.tables.ProcessDatabase;
import org.json.JSONArray;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


@WebServlet(name = "CreateProcess")
public class CreateProcess extends HttpServlet {
    private ProcessDatabase processTable = new ProcessDatabase();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = "";
        Scanner scanner = new Scanner(new InputStreamReader(request.getInputStream(), "UTF-8"));
        while (scanner.hasNextLine()) {
            json += scanner.nextLine();
        }
        System.out.println(json);
        JSONArray readingJSONArray = new JSONArray();
        try {
            readingJSONArray = new JSONArray(json);
            Process newProcess = null;
            String processName = readingJSONArray.getJSONObject(0).getString("processName");
            newProcess = new Process(processName);
            for (int i = 1; i < readingJSONArray.length(); i++) {
                int stepID = readingJSONArray.getJSONObject(i).getInt("stepID");
                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
                int rejectStepID = readingJSONArray.getJSONObject(i).getInt("rejectStepID");
                int departmentID = readingJSONArray.getJSONObject(i).getInt("departmentID");
                Step step = new Step(stepID, acceptStepID, rejectStepID, departmentID);
                newProcess.addStep(step);
            }
            processTable.addNewProcessToDB(newProcess);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }

        //send message to android device
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
