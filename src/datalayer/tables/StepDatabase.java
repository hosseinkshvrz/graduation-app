package datalayer.tables;

import appLayer.Step;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StepDatabase {
    private final String tableName = "steps";

    public Step getStep(int stepID) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = \"" + stepID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Step step = null;
        try {
            rs.next();
            String stepName = rs.getString("name");
            int acceptStepID = rs.getInt("stepIDaccept");
            int rejectStepID = rs.getInt("stepIDreject");
            int processID = rs.getInt("processID");
            int departmentID = rs.getInt("departmentID");
            step = new Step(stepName, acceptStepID, rejectStepID, processID, departmentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return step;
    }

    public void addNewStepToDB(Step step) {
        String name = step.getStepName();
        DatabaseExecutor de = new DatabaseExecutor();
        String sql = "INSERT INTO " + tableName + " (name, stepIDaccept, stepIDReject, processID, departmentID)" +
                " VALUE ('" + name + "', " + step.getAcceptStepID() + ", " + step.getRejectStepID() + ", " +
                step.getProcessID() + ", " + step.getDepartmentID() + ");";
        System.out.println(sql);
        int stepID = de.executeAutoIncrementUpdateQuery(sql);
        step.setStepID(stepID);
        de.closeConnection();
    }

}
