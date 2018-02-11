package datalayer.tables.steps;


import appLayer.steps.Step;
import appLayer.steps.StepInstance;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StepInstanceDatabase extends AbstractStepDatabase {
    private final String tableName = "step_instance";
    public void addNewStepInstanceToDB(StepInstance stepInstance) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("stepID", String.valueOf(stepInstance.getStepID()));
        parameters.put("pInstanceID", String.valueOf(stepInstance.getProcessInstanceID()));
        parameters.put("personnelID", String.valueOf(stepInstance.getPersonnelID()));
        parameters.put("start", stepInstance.getStart());
        parameters.put("studentID", stepInstance.getStudentID());
        parameters.put("result", stepInstance.getResult());
        super.addStepToDB(stepInstance, tableName, parameters);
    }

    public int getStepID(int stepInstanceID) {
        String sql = "SELECT stepID FROM " + tableName + " WHERE sInstanceID = " + stepInstanceID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        int stepID = -1;
        try {
            rs.next();
            stepID = rs.getInt("stepID");
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stepID;
    }

    public int getProcessInstanceID(int stepInstanceID) {
        String sql = "SELECT pInstanceID FROM " + tableName + " WHERE sInstanceID = " + stepInstanceID;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        int pInstanceID = -1;
        try {
            rs.next();
            pInstanceID = rs.getInt("pInstanceID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pInstanceID;
    }

    public StepInstance getStepInstance(int stepInstanceID) {
        String sql = "SELECT * FROM " + tableName + " WHERE sInstanceID = \"" + stepInstanceID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        StepInstance stepInstance = null;
        try {
            rs.next();
            int stepID = rs.getInt("stepID");
            int pInstanceID = rs.getInt("pInstanceID");
            String personnelID = rs.getString("personnelID");
            String startTime = rs.getString("start");
            String result = rs.getString("result");
            String studentID = rs.getString("studentID");
            stepInstance = new StepInstance(stepID, pInstanceID, personnelID, startTime, studentID, result);
            stepInstance.setID(stepInstanceID);
            stepInstance.setEnd(rs.getString("end"));
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stepInstance;
    }

    public void finishStep(String endTime, String result, int stepInstanceID) {
        String sql = "UPDATE " + tableName + " SET end = '" + endTime + "' AND result = " + result +" WHERE sInstanceID = " + stepInstanceID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }

    public ArrayList<StepInstance> getPostRelatedStepInstances(String personnelID) {
        String sql = "SELECT * FROM " + tableName + " WHERE personnelID = " + personnelID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        ArrayList<StepInstance> postStepInstances = new ArrayList<>();
        try {
            while (rs.next()) {
                int stepInstanceID = rs.getInt("sInstanceID");
                int stepID = rs.getInt("stepID");
                int pInstanceID = rs.getInt("pInstanceID");
                String start = rs.getString("start");
                String result = rs.getString("result");
                String studentID = rs.getString("studentID");
                StepInstance stepInstance = new StepInstance(stepID, pInstanceID, personnelID, start, studentID, result);
                stepInstance.setID(stepInstanceID);
                stepInstance.setEnd(rs.getString("end"));
                postStepInstances.add(stepInstance);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postStepInstances;
    }

    public StepInstance getStepInstance(int stepID, String personnelID) {
        String sql = "SELECT * FROM " + tableName + " WHERE stepID = '" + stepID + "' AND personnelID = " + personnelID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        StepInstance stepInstance = null;
        try {
            rs.next();
            int stepInstanceID = rs.getInt("sInstanceID");
            int pInstanceID = rs.getInt("pInstanceID");
            String startTime = rs.getString("start");
            String result = rs.getString("result");
            String studentID = rs.getString("studentID");
            stepInstance = new StepInstance(stepID, pInstanceID, personnelID, startTime, studentID, result);
            stepInstance.setID(stepInstanceID);
            stepInstance.setEnd(rs.getString("end"));
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stepInstance;
    }

    public ArrayList<StepInstance> getStudentSteps(String studentID) {
        ArrayList<StepInstance> studentSteps = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = " + studentID;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        try {
            while (rs.next()) {
                studentSteps.add(getStepInstance(rs.getInt("sInstanceID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentSteps;
    }
}
