package datalayer;

import appLayer.StudentUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDatabase
{
    public AdminDatabase() {

    }

    public boolean isValidUserLogin(String id, String password) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        String sql = "SELECT * FROM admins WHERE id = \"" + id + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
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
