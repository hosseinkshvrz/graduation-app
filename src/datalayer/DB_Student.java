package datalayer;

import appLayer.StudentUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB_Student
{
    public DB_Student() {

    }

    public boolean isValidUserLogin(String studentID, String password) throws ClassNotFoundException, SQLException {
        boolean isValidUser = false;
        String sql = "SELECT * FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
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

    public StudentUser getStudent(String studentID, String password) throws SQLException {
        String sql = "SELECT * FROM students WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
        ResultSet rs = connectionToDB.executeQuery(sql);
        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String id = rs.getString("studentID");
        String name = rs.getString("name");
        String pass = rs.getString("password");
        String mail = rs.getString("email");

        rs.close();
        connectionToDB.closeConnection();
        return new StudentUser(id, name, pass, mail);
    }

    public boolean addNewStudentToDB(StudentUser student) {
        boolean addIsDone;
        String studentID = student.getStudentID();
        String password = student.getPassword();
        String name = student.getName();
        String email = student.getEmail();
        String sql = "INSERT INTO students (studentID, name, password, email) VALUES ('" + studentID + "', '" + name + "', '" + password + "', '" + email + "')";
        System.out.println(sql);
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
        if (connectionToDB.executeUpdateQuery(sql) > 0) {
            addIsDone = true;
        }
        else {
            addIsDone = false;
        }
        connectionToDB.closeConnection();
        return addIsDone;
    }

    public ArrayList<StudentUser> getListOfAllStudents() throws SQLException {
        DB_Connector connectionToDB = new DB_Connector();
        connectionToDB.makeConnection();
        String sql = "SELECT name FROM students";
        ResultSet rs = connectionToDB.executeQuery(sql);

        ArrayList<StudentUser> students = new ArrayList<>();
        while(rs.next())
        {
            String studentID = rs.getString(0);
            String name = rs.getString(1);
            String password = rs.getString(2);
            String email = rs.getString(3);
            StudentUser s = new StudentUser(studentID, name, password, email);
            students.add(s);
        }
        rs.close();
        connectionToDB.closeConnection();
        return students;
    }
}
