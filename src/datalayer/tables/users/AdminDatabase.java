package datalayer.tables.users;

import appLayer.users.AdminUser;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDatabase extends AbstractUserDatabase {
    private final String tableName = "admins";

    public boolean isValidAdminLogin(String id, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = \"" + id + "\" AND password = \"" + password + "\"";
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

    public AdminUser getUser(String userID) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = \"" + userID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int adminID = rs.getInt("id");
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String pass = rs.getString("password");
        String mail = rs.getString("email");
        rs.close();
        de.closeConnection();
        return new AdminUser(adminID, firstName, lastName, pass, mail);
    }
}
