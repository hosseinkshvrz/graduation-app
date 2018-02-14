package datalayer.tables.steps;

import appLayer.steps.Step;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StepDatabase extends AbstractStepDatabase{
    private final String tableName = "steps";

    public Step getStep(int stepID) {
        String sql = "SELECT * FROM " + tableName + " WHERE stepID = " + stepID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Step step = null;
        try {
            rs.next();
            String stepName = rs.getString("name");
            int processID = rs.getInt("processID");
            String departmentID = rs.getString("departmentID");
            int isFirstStep = rs.getInt("firstStep");
            step = new Step(stepName, processID, departmentID, (isFirstStep == 1));
            step.setID(stepID);
            step.setAcceptStepID(rs.getInt("stepIDaccept"));
            step.setRejectStepID(rs.getInt("stepIDreject"));
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return step;
    }

    public void addNewStepToDB(Step step) {
        int firstStep = 0;
        if (step.isFirstStep()) {
            firstStep = 1;
        }
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", step.getStepName());
        parameters.put("processID", String.valueOf(step.getProcessID()));
        parameters.put("departmentID", step.getDepartmentID());
        parameters.put("firstStep", String.valueOf(firstStep));
        super.addStepToDB(step, tableName, parameters);
    }

    public Step getStep(String stepName) {
        String sql = "SELECT * FROM " + tableName + " WHERE name = '" + stepName + "'";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Step step = null;
        try {
            rs.next();
            int stepID = rs.getInt("stepID");
            int processID = rs.getInt("processID");
            String departmentID = rs.getString("departmentID");
            int isFirstStep = rs.getInt("firstStep");
            step = new Step(stepName, processID, departmentID, (isFirstStep == 1));
            step.setID(stepID);
            step.setAcceptStepID(rs.getInt("stepIDaccept"));
            step.setAcceptStepID(rs.getInt("stepIDreject"));
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return step;
    }

    public void setAcceptStepID(Integer key, Integer value) {
        String sql = "UPDATE " + tableName + " SET stepIDaccept = " + value + " WHERE stepID = " + key;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }

    public void setRejectStepID(Integer key, Integer value) {
        String sql = "UPDATE " + tableName + " SET stepIDreject = " + value + " WHERE stepID = " + key;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }
}
