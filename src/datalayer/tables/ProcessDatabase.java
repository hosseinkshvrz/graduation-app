package datalayer.tables;
import appLayer.Process;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessDatabase {
    private final String tableName = "processes";

    public Process getProcess (int processID) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE processID = \"" + processID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String processName = rs.getString("name");
        int index = 2;
        Process process = new Process(processName);
        while (rs.next()) {
            int stepID = rs.getInt(index);
            process.addStep(stepID);
            index++;
        }
        rs.close();
        de.closeConnection();
        return process;
    }

    public void addNewProcessToDB(Process process) {
        String name = process.getName();
        int numberOfColumns;
        String sql = "SELECT count(*)\n" +
                "FROM information_schema.columns\n" +
                "WHERE table_name = '" + tableName + "'";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        try {
            rs.next();
            numberOfColumns = rs.getInt(1);
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
                    sql = "ALTER TABLE " + tableName + "\nADD COLUMN step" + (i+1) + " INT NULL AFTER name," +
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
            /* create sql query */
            sql = "INSERT INTO " + tableName + " (name";
            for (int i = 0; i < process.getProcessSteps().size(); i++) {
                sql += ", step" + (i+1);
            }
            sql += ") VALUES ('" + name;
            for (int i = 0; i < process.getProcessSteps().size(); i++) {
                sql += "', '" + process.getProcessSteps().get(i).getStepID();
            }
            sql += "')";
            System.out.println(sql);
            //may be needs to check result was successful
            int processID = de.executeAutoIncrementUpdateQuery(sql);
            process.setProcessID(processID);
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
