package datalayer.tables;

import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProcessRequestsDatabase {
    private final String tableName = "process_requests";

    public boolean addRequest(String studentID, int processID) {
        boolean addIsDone;
        String sql = "INSERT " + tableName + " (studentID, processID) VALUES ('"
                + studentID + "', '" + processID + "')";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        addIsDone = de.executeUpdateQuery(sql);

        de.closeConnection();
        return addIsDone;
    }

    public HashMap<String, Integer> getRequests() {
        DatabaseExecutor de = new DatabaseExecutor();
        HashMap<String, Integer> requests = new HashMap<>();
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + tableName;
            rs = de.executeGetQuery(sql);
            while(rs.next()) {
                requests.put(rs.getString("studentID"), rs.getInt("processID"));
            }
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}
