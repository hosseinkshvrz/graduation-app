package datalayer.tables.steps;

import appLayer.steps.IStep;
import datalayer.DatabaseExecutor;

import java.util.HashMap;

public class AbstractStepDatabase {

    public void addStepToDB(IStep step, String tableName, HashMap<String, String> parameters) {
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
            sql += ("'" + value + "', ");
        }
        if (sql.charAt(sql.length()-2) == ',') {
            sql = sql.substring(0, sql.length()-2);
        }
        sql += ");";
        System.out.println(sql);
        int stepInstanceID = de.executeAutoIncrementUpdateQuery(sql);
        step.setID(stepInstanceID);
        de.closeConnection();
    }
}
