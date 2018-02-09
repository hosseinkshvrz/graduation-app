package datalayer.tables.processes;


import appLayer.processes.ProcessInstance;
import appLayer.steps.StepInstance;
import datalayer.DatabaseExecutor;
import datalayer.tables.steps.StepInstanceDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProcessInstanceDatabase extends AbstractProcessDatabase {
    private final String tableName = "process_instance";
    private final StepInstanceDatabase stepInstanceTable = new StepInstanceDatabase();

    public void addNewProcessInstanceToDB(ProcessInstance processInstance) {
        int processID = processInstance.getProcessID();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("processID", String.valueOf(processID));
        super.addProcessToDB(processInstance, tableName, parameters);
    }

    public void addStepInstancesToProcessInstance(ProcessInstance processInstance) {
        HashMap<String, String> parameters = new HashMap<>();
        for (int i = 0; i < processInstance.getStepInstances().size(); i++) {
            parameters.put("step" + (i+1), processInstance.getStepInstances().get(i).toString());
        }
        super.addAndFillColumns(processInstance, tableName, parameters, "step_instance", "sInstanceID");
    }

    public ProcessInstance getProcessInstance(int processInstanceID) {
        String sql = "SELECT * FROM " + tableName + " WHERE pInstanceID = " + processInstanceID;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        ProcessInstance processInstance = null;
        try {
            rs.next();
            int processID = rs.getInt("processID");
            processInstance = new ProcessInstance(processID);
            int pInstanceID = rs.getInt("pInstanceID");
            processInstance.setID(pInstanceID);
            int numberOfColumns = super.getNumberOfTableColumns(tableName);
            for (int i = 0; i < numberOfColumns-2; i++) {
                int stepInstanceID = rs.getInt("stepInstance" + (i+1));
                StepInstance stepInstance = stepInstanceTable.getStepInstance(stepInstanceID);
                processInstance.addStepInstance(stepInstance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processInstance;
    }
}
