package webapp;

import appLayer.Process;
import appLayer.Step;
import datalayer.tables.ProcessDatabase;
import datalayer.tables.StepDatabase;
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
    private StepDatabase stepTable = new StepDatabase();
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
            Process process = null;
            String processName = readingJSONArray.getJSONObject(0).getString("processName");
            process = new Process(processName);
            processTable.addNewProcessToDB(process);
            for (int i = 1; i < readingJSONArray.length(); i++) {
                String stepName = readingJSONArray.getJSONObject(i).getString("stepName");
                int acceptStepID = readingJSONArray.getJSONObject(i).getInt("acceptStepID");
                int rejectStepID = readingJSONArray.getJSONObject(i).getInt("rejectStepID");
                int departmentID = readingJSONArray.getJSONObject(i).getInt("departmentID");
                Step step = new Step(stepName, acceptStepID, rejectStepID, process.getProcessID(), departmentID);
                stepTable.addNewStepToDB(step);
                process.addStep(step);
            }
            processTable.addStepsToProcess(process);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }

        //send message to android device
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
