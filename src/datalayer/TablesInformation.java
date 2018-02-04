package datalayer;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TablesInformation {

    public static int getNumberOfTableColumns(String tableName) {
        String sql = "SELECT count(*)\n" +
                "FROM information_schema.columns\n" +
                "WHERE table_name = '" + tableName + "'";
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        int numberOfColumns = 0;
        try {
            rs.next();
            numberOfColumns = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfColumns;
    }
}
