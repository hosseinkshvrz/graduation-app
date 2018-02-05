package datalayer.tables.processes;

import appLayer.processes.IProcess;
import appLayer.processes.Process;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractProcessDatabase {
    public void addProcessToDB (IProcess proc, String tableName, HashMap<String, String> parameters) {
        DatabaseExecutor de = new DatabaseExecutor();
        String sql = "INSERT INTO " + tableName + " (";
        for (String key :
                parameters.keySet()) {
            sql += (key + ", ");
        }
        if (sql.charAt(sql.length()-2) == ',') {
            sql = sql.substring(0, sql.length()-2);
        }
        sql += ") VALUE (";
        for (String key:
                parameters.keySet()) {
            String value = parameters.get(key);
            sql += (value + ", ");
        }
        if (sql.charAt(sql.length()-2) == ',') {
            sql = sql.substring(0, sql.length()-2);
        }
        sql += ");";
        System.out.println(sql);
        int processInstanceID = de.executeAutoIncrementUpdateQuery(sql);
        proc.setProcessID(processInstanceID);
        de.closeConnection();
    }

    public void addAndFillColumns(IProcess proc, String tableName, HashMap<String, String> parameters, String refrenceTable, String refrencedColumn) {
        int numberOfColumns = getNumberOfTableColumns(tableName);
        String sql;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = null;
        try {
             /*
             ALTER TABLE processes
             ADD COLUMN step3 INT NULL,
             ADD INDEX (step3 ASC);
             ALTER TABLE processes
             ADD CONSTRAINT
             FOREIGN KEY (step3)
             REFERENCES steps (stepID)
             ON DELETE CASCADE
             ON UPDATE CASCADE;
             */
             /* add column to table */
            int index = 0;
            for (String key :
                    parameters.keySet()) {
                if (index >= numberOfColumns - 2) {
                    sql = "ALTER TABLE " + tableName
                            + "\nADD COLUMN " + key + " INT NULL,"
                            + "\nADD INDEX (" + key + " ASC);";
                    System.out.println(sql);
                    de.executeUpdateQuery(sql);
                    sql = "ALTER TABLE " + tableName
                            + "\nADD CONSTRAINT FOREIGN KEY (" + key + ")"
                            + "\nREFERENCES " + refrenceTable + " (" + refrencedColumn + ")"
                            + "\nON DELETE CASCADE"
                            + "\nON UPDATE CASCADE;";
                    System.out.println(sql);
                    de.executeUpdateQuery(sql);
                }
                index++;
            }
            sql = "UPDATE " + tableName + "\nSET ";
            for (String key :
                    parameters.keySet()) {
                sql += (key + " = " + parameters.get(key) + ", ");
            }
            if (sql.charAt(sql.length()-2) == ',') {
                sql = sql.substring(0, sql.length()-2);
            }
            if (proc instanceof Process) {
                sql += "WHERE processID = " + proc.getID();
            }
            else {
                sql += "WHERE pInstanceID = " + proc.getID();
            }
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

    public static int getNumberOfTableColumns(String tableName) {
        String sql = "SELECT count(*)\n" +
                "FROM information_schema.columns\n" +
                "WHERE table_name = '" + tableName + "'";
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        int numberOfColumns = 0;
        try {
            rs.next();
            numberOfColumns = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfColumns;
    }
}
