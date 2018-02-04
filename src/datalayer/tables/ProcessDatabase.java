package datalayer.tables;
import appLayer.Process;
import appLayer.Step;
import datalayer.DatabaseExecutor;
import datalayer.TablesInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessDatabase {
    private final String tableName = "processes";
    private StepDatabase stepTable = new StepDatabase();

    public Process getProcess (String processName) {
        int numberOfColumns = TablesInformation.getNumberOfTableColumns(tableName);
        String sql = "SELECT * FROM " + tableName + " WHERE name = \"" + processName + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Process process = null;
        try {
            rs.next();
            int processID = rs.getInt("processID");
            String status = rs.getString("status");
            process = new Process(processID, processName, status);
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
        DatabaseExecutor de = new DatabaseExecutor();
        String sql = "INSERT INTO " + tableName + " (name) VALUE ('" + name + "');";
        int processID = de.executeAutoIncrementUpdateQuery(sql);
        process.setProcessID(processID);
        de.closeConnection();
    }
     public void addStepsToProcess (Process process) {
         int numberOfColumns = TablesInformation.getNumberOfTableColumns(tableName);
         String sql;
         DatabaseExecutor de = new DatabaseExecutor();
         ResultSet rs = null;
         try {
             /*
             ALTER TABLE `graduation`.`processes`
             ADD COLUMN `step1` INT NULL AFTER `name`,
             ADD INDEX `fk_process_step_idx` (`step1` ASC);
             ALTER TABLE `graduation`.`processes`
             ADD CONSTRAINT `fk_process_step`
             FOREIGN KEY (`step1`)
             REFERENCES `graduation`.`steps` (`stepID`)
             ON DELETE CASCADE
             ON UPDATE CASCADE;
             */
             /* add column to table */
             for (int i = 0; i < process.getProcessSteps().size(); i++) {
                 if (i >= numberOfColumns - 2) {
                     sql = "ALTER TABLE " + tableName + "\nADD COLUMN step" + (i+1) + " INT NULL," +
                             "\nADD INDEX fk_process_step" + (i+1) + "_idx (step" + (i+1) + " ASC);";
                     System.out.println(sql);
                     de.executeUpdateQuery(sql);
                     sql = "ALTER TABLE " + tableName + "\nADD CONSTRAINT fk_process_step" + (i+1) + "" +
                             "\nFOREIGN KEY (step" + (i + 1) + ")" +
                             "\nREFERENCES steps (stepID)" +
                             "\nON DELETE CASCADE" +
                             "\nON UPDATE CASCADE;";
                     System.out.println(sql);
                     de.executeUpdateQuery(sql);
                 }
             }
             sql = "UPDATE " + tableName + "\nSET name = '" + process.getName() + "'";
             for (int i = 0; i < process.getProcessSteps().size(); i++) {
                 sql += ", step" + (i+1) + " = " + process.getProcessSteps().get(i).getStepID();
             }
             sql += "\nWHERE processID = " + process.getProcessID();
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
