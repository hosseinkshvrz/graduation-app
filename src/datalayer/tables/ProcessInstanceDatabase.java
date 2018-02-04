package datalayer.tables;


import appLayer.ProcessInstance;
import datalayer.DatabaseExecutor;
import datalayer.TablesInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

//this class and ProcessDatabase class should be extended from an abstract class
//it should happen for StepDatabase and StepInstanceDatabase too.
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

    public void addSingleStepInstanceToProcessInstance(ProcessInstance processInstance) {
        int numberOfColumns = TablesInformation.getNumberOfTableColumns(tableName);
        String sql;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = null;
        int index = processInstance.getStepInstances().size() - 1;
        try {
            if (index >= numberOfColumns - 2) {
                sql = "ALTER TABLE " + tableName + "\nADD COLUMN stepInstance" + (index+1) + " INT NULL,"
                        + "\nADD INDEX fk_processInstance_stepInstance" + (index+1)
                        + "_idx (stepInstance" + (index+1) + " ASC);";
                System.out.println(sql);
                de.executeUpdateQuery(sql);
                sql = "ALTER TABLE " + tableName + "\nADD CONSTRAINT fk_processInstance_stepInstance" + (index+1)
                        + "\nFOREIGN KEY (stepInstance" + (index+1) + ")"
                        + "\nREFERENCES step_instance (sInstanceID)"
                        + "\nON DELETE CASCADE"
                        + "\nON UPDATE CASCADE;";
                System.out.println(sql);
                de.executeUpdateQuery(sql);
            }
            sql = "UPDATE " + tableName + "\nSET stepInstance" + (index+1)
                    + " = " + processInstance.getStepInstances().get(index).getStepInstanceID();
            sql += "\nWHERE pInstanceID = " + processInstance.getProcessInstanceID();
            System.out.println(sql);
            //may be needs to check result was successful
            de.executeUpdateQuery(sql);
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
