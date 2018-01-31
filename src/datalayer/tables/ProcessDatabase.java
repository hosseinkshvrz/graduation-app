package datalayer.tables;
import appLayer.Process;
import datalayer.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcessDatabase {
    private final String tableName = "processes";

    public Process getProcess (int processID) throws SQLException {
        //get from db should be in one method;
        String sql = "SELECT * FROM " + tableName + " WHERE processID = \"" + processID + "\"";
        System.out.println(sql);
        DatabaseConnector connectionToDB = new DatabaseConnector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
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
        connectionToDB.closeConnection();
        return process;
    }

    public boolean addNewProcessToDB(Process process) {
        boolean addIsDone = false;
        String name = process.getName();
        int numberOfColumns;
        String sql = "SELECT count(*)\n" +
                "FROM information_schema.columns\n" +
                "WHERE table_name = '" + tableName + "'";
        System.out.println(sql);
        DatabaseConnector connectionToDB = new DatabaseConnector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
        try {
            rs.next();
            numberOfColumns = rs.getInt(1);
            for (int i = 0; i < process.getProcessSteps().size(); i++) {
                if (i >= numberOfColumns - 2) {
                    //add column that can be null
                    numberOfColumns++;
                }
            }
            /* create sql query **/
            sql = "INSERT INTO " + tableName + " (name";
            for (int i = 0; i < numberOfColumns - 2; i++) {
                sql += ", step" + (i+1);
            }
            sql += ") VALUES ('" + name;
            for (int i = 0; i < numberOfColumns - 2; i++) {
                sql += ", '" + process.getProcessSteps().get(i).getStepID();
            }
            sql += "')";
            if (connectionToDB.executeUpdateQuery(sql) > 0) {
                addIsDone = true;
            }
            else {
                addIsDone = false;
            }
            connectionToDB.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return addIsDone;
    }
}
