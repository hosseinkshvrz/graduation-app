package datalayer.tables.steps;

import appLayer.steps.Step;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StepDatabase extends AbstractStepDatabase{
    private final String tableName = "steps";

    public Step getStep(int stepID) {
        String sql = "SELECT * FROM " + tableName + " WHERE stepID = \"" + stepID + "\"";
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
            String departmentID = rs.getString("departmentID");
            int isFirstStep = rs.getInt("isFirstStep");
            step = new Step(stepName, acceptStepID, rejectStepID, processID, departmentID, (isFirstStep == 1));
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
        parameters.put("stepIDaccept", String.valueOf(step.getAcceptStepID()));
        parameters.put("stepIDreject", String.valueOf(step.getRejectStepID()));
        parameters.put("processID", String.valueOf(step.getProcessID()));
        parameters.put("departmentID", step.getDepartmentID());
        parameters.put("firstStep", String.valueOf(firstStep));
        super.addStepToDB(step, tableName, parameters);
    }

//    public Step getAcceptStep(int currentStepID) {
//        String sql = "SELECT stepIDaccept FROM " + tableName + " WHERE stepID = " + currentStepID;
//        DatabaseExecutor de = new DatabaseExecutor();
//        ResultSet rs = de.executeGetQuery(sql);
//    }
//
//    public Step getRejectStep(int currentStepID) {
//        return null;
//    }
}
