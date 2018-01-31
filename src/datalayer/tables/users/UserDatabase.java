package datalayer.tables.users;


import appLayer.users.User;
import datalayer.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDatabase {
    boolean checkUserExistenceWithDatabase(String sql) throws ClassNotFoundException, SQLException {
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
    abstract User getUser(String userID) throws SQLException;
}
