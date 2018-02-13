package datalayer.tables;

import appLayer.ProcessRequest;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessRequestsDatabase {
    private final String tableName = "process_requests";

    public boolean addRequest(String studentID, int processID, String status) {
        boolean addIsDone;
        String sql = "INSERT INTO " + tableName + " (studentID, processID, status) VALUES ('"
                + studentID + "', " + processID  + ", '" + status + "')";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        addIsDone = de.executeUpdateQuery(sql);

        de.closeConnection();
        return addIsDone;
    }

    public ArrayList<ProcessRequest> getRequests() {
        DatabaseExecutor de = new DatabaseExecutor();
        ArrayList<ProcessRequest> requests = new ArrayList<>();
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE status = 'stall';";
            rs = de.executeGetQuery(sql);
            while(rs.next()) {
                String studentID = rs.getString("studentID");
                int processID = rs.getInt("processID");
                String status = rs.getString("status");
                ProcessRequest request = new ProcessRequest(studentID, processID, status);
                requests.add(request);
            }
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void changeStatus(String studentID, int processID, String result) {
        String status;
        if (result.equals("accept")) {
            status = "yes";
        }
        else {
            status = "no";
        }
        String sql = "UPDATE " + tableName + " SET status = '" + status + "' WHERE studentID = '" + studentID
                + "' AND processID = " + processID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }

    public ArrayList<ProcessRequest> getStudentRequests(String studentID) {
        DatabaseExecutor de = new DatabaseExecutor();
        ArrayList<ProcessRequest> studentRequests = new ArrayList<>();
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE studentID = '" + studentID + "'";
            rs = de.executeGetQuery(sql);
            while(rs.next()) {
                int processID = rs.getInt("processID");
                String status = rs.getString("status");
                ProcessRequest request = new ProcessRequest(studentID, processID, status);
                studentRequests.add(request);
            }
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return studentRequests;
    }
}
