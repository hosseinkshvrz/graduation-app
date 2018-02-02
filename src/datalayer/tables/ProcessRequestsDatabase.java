package datalayer.tables;

import datalayer.DatabaseExecutor;

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
}
