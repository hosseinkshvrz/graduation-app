package datalayer;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/graduation";

    static final String USER ="graduationuser";
    static final String PASS = "12345621";
    java.sql.Connection connection = null;
    java.sql.Statement statement = null;

    public DatabaseConnector() {
    }

    public void makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Connection to database");
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Creating statement...");
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        System.out.println("Closing DB Connection - Goodbye!");
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public int executeUpdateQuery(String sql) {
        int updateResult = 0;
        try {
            updateResult = statement.executeUpdate(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return updateResult;
    }
}

