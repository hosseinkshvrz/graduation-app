package datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Student
{
    public DB_Student() {

    }

    public boolean isValidUserLogin(String studentID, String password) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        String sql;
        sql = "SELECT * FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
        if (rs.next()) {
            isValidUser = true;
        }
        rs.close();
        connectionToDB.closeConnection();
        return isValidUser;
    }

    public String getStudentName(String studentID, String password) {
        String sql;
        sql = "SELECT name FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
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
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionToDB.closeConnection();
        return name;
    }

    public boolean addNewStudentToDB(String studentID, String password, String name, String email) {
        boolean addIsDone = false;
        String sql;
        sql = "INSERT INTO students (studentID, name, password, email) VALUES ('" + studentID + "', '" + name + "', '" + password + "', '" + email + "')";
        System.out.println(sql);
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
//        try {
//            addIsDone = rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        connectionToDB.closeConnection();
        return true;
    }
}
