package datalayer.tables;


import appLayer.ProcessInstance;
import datalayer.DatabaseExecutor;

public class ProcessInstanceDatabase {
    private final String tableName = "process_instance";
    public void addNewProcessInstanceToDB(ProcessInstance processInstance) {
        int processID = processInstance.getProcessID();
        DatabaseExecutor de = new DatabaseExecutor();
        String sql = "INSERT INTO " + tableName + " (processID) VALUE (" + processID + ");";
        int processInstanceID = de.executeAutoIncrementUpdateQuery(sql);
        processInstance.setProcessInstanceID(processInstanceID);
        de.closeConnection();
    }
}
