package datalayer.tables.users;


import appLayer.users.User;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDatabase {
    boolean checkUserExistenceWithDatabase(String sql) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        if (rs.next()) {
            isValidUser = true;
        }
        rs.close();
        de.closeConnection();
        return isValidUser;
    }
    abstract User getUser(String userID) throws SQLException;
}
