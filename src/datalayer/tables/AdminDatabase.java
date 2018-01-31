package datalayer.tables;

import java.sql.SQLException;

public class AdminDatabase extends UserDatabase{
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

}
