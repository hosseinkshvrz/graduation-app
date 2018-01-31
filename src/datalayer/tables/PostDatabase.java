package datalayer.tables;

import java.sql.SQLException;

public class PostDatabase extends UserDatabase {
    private final String tableName = "posts";


    public boolean isValidPostLogin(String personnelID, String password) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = \"" + personnelID + "\" AND password = \"" + password + "\"";
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
