package datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Student
{
    DB_Connector connectionToDB;
    public DB_Student() {
        connectionToDB = DB_Connector.getInstance();
    }

    public boolean isValidUserLogin(String studentID, String password) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        String sql;
        sql = "SELECT * FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        ResultSet rs = connectionToDB.executeQuery(sql);
        if (rs.next()) {
            isValidUser = true;
        }
//        rs.close();
//        System.out.println("Closing DB Connection - Goodbye!");
        return isValidUser;
    }

    public String getStudentName(String studentID, String password) {
        String sql;
        sql = "SELECT name FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        ResultSet rs = connectionToDB.executeQuery(sql);
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String name = "";
        try {
            name = rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try {
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Closing DB Connection - Goodbye!");
        return name;
    }
}
