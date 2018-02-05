package datalayer.tables.processes;
import appLayer.processes.Process;
import appLayer.steps.Step;
import datalayer.DatabaseExecutor;
import datalayer.tables.steps.StepDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessDatabase extends AbstractProcessDatabase {
    private final String tableName = "processes";
    private StepDatabase stepTable = new StepDatabase();

    public Process getProcess (String processName) {
        int numberOfColumns = super.getNumberOfTableColumns(tableName);
        String sql = "SELECT * FROM " + tableName + " WHERE name = \"" + processName + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Process process = null;
        try {
            rs.next();
            int processID = rs.getInt("processID");
            process = new Process(processID, processName);
            int stepID;
            for (int i = 0; i < numberOfColumns - 2; i++) {
                stepID = rs.getInt("step" + (i+1));
                if (stepID != 0) {      //for processes which doesn't have that step.
                    Step step = stepTable.getStep(stepID);
                    process.addStep(step);
                }
            }
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return process;
    }

    public ArrayList<Process> getProcesses() {
        DatabaseExecutor de = new DatabaseExecutor();
        ArrayList<String> processNames = new ArrayList<>();
        ArrayList<Process> resultProcesses = new ArrayList<>();
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + tableName;
            rs = de.executeGetQuery(sql);
            while(rs.next()) {
                processNames.add(rs.getString("name"));
            }
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < processNames.size(); i++) {
            Process process = getProcess(processNames.get(i));
            resultProcesses.add(process);
        }
        return resultProcesses;
    }

    public void addNewProcessToDB(Process process) {
        String name = process.getName();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        super.addProcessToDB(process, tableName, parameters);
    }
     public void addStepsToProcess (Process process) {
        HashMap<String, String> parameters = new HashMap<>();
        for (int i = 0; i < process.getProcessSteps().size(); i++) {
            parameters.put("step" + (i+1), process.getProcessSteps().get(i).toString());
        }
        super.addAndFillColumns(process, tableName, parameters, "steps", "stepID");
     }
}