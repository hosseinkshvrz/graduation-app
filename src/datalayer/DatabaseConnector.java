package datalayer;

import com.mysql.jdbc.log.StandardLogger;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {

    private final StandardLogger log = new StandardLogger("database");
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/graduation";

    static final String USER ="graduationuser";
    static final String PASS = "12345621";

    java.sql.Connection connection = null;
    java.sql.Statement statement = null;

    public void makeConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connection to database");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");
            statement = connection.createStatement();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        System.out.println("Closing DB Connection - Goodbye!");
        try {
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        }
        catch (SQLException e) {
            log.logError("******* Student not found *******");
        }
        return rs;
    }
    public int executeUpdateQuery(String sql) {
        int updateResult = 0;
        try {
            updateResult = statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            log.logError("******* Student already exists *******");
        }

        return updateResult;
    }
}

