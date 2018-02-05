package datalayer.tables.users;

import appLayer.users.PostUser;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDatabase extends AbstractUserDatabase {
    private final String tableName = "posts";


    public boolean isValidPostLogin(String personnelID, String password) {
        String sql = "SELECT * FROM " + tableName + " WHERE personnelID = \"" + personnelID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        try {
            return checkUserExistenceWithDatabase(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PostUser getUser(String userID) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE personnelID = \"" + userID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String id = rs.getString("personnelID");
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String pass = rs.getString("password");
        String depID = rs.getString("departmentID");
        String mail = rs.getString("email");
        rs.close();
        de.closeConnection();
        return new PostUser(id, firstName, lastName, pass, depID, mail);
    }

    public String getAvailablePostID() {
        String sql = "SELECT personnelID FROM " + tableName;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        String id = "";
        try {
            rs.next();
            id = rs.getString("personnelID");
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
