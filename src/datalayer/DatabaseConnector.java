package datalayer;

import java.sql.*;

public class DatabaseConnector {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/graduation";
    static final String USER ="graduationuser";
    static final String PASS = "12345621";

    Connection connection;


    public Statement makeConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connection to database");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");
            return connection.createStatement();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        System.out.println("Closing DB Connection - Goodbye!");
        try {
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

