package datalayer.tables;


import datalayer.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDatabase {
    protected boolean checkUserExistenceWithDatabase(String sql) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        DatabaseConnector connectionToDB = new DatabaseConnector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
        if (rs.next()) {
            isValidUser = true;
        }
        rs.close();
        connectionToDB.closeConnection();
        return isValidUser;
    }
}
