package datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDatabase {
    public PostDatabase() {

    }

    public boolean isValidUserLogin(String personnelID, String password) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        String sql = "SELECT * FROM posts WHERE id = \"" + personnelID + "\" AND password = \"" + password + "\"";
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
