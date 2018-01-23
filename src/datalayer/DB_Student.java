package datalayer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Student
{
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/graduation";

    //Database credentials
    static final String USER ="graduationuser";
    static final String PASS = "12345621";

    public boolean isValidUserLogin(String studentID, String password) throws ClassNotFoundException, SQLException
    {
        boolean isValidUser = false;
        java.sql.Connection connection = null;
        java.sql.Statement statement = null;
        String sql = "";

        try
        {

            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connection to database");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            statement = connection.createStatement();

            sql = "SELECT * FROM students WHERE studentID =\"" + studentID + "\"AND password = \"" + password + "\"";
            System.out.println(sql);

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                isValidUser = true;
            }

            rs.close();
            statement.close();
            connection.close();
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(connection != null)
                {
                    statement.close();
                }
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
            try
            {
                if(connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
        System.out.println("Closing DB Connection - Goodbye!");



        return isValidUser;
    }
}
