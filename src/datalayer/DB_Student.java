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
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        String pass = rs.getString("password");
        String mail = rs.getString("email");
        int day = Integer.parseInt(rs.getString("birthday").substring(8, 10));
        int month = Integer.parseInt(rs.getString("birthday").substring(5, 7));
        int year = Integer.parseInt(rs.getString("birthday").substring(0, 4));
        rs.close();
        connectionToDB.closeConnection();
        return new StudentUser(id, firstName, lastName, pass, mail, day, month, year);
    }

    public boolean addNewStudentToDB(StudentUser student) {
        boolean addIsDone;
        String studentID = student.getStudentID();
        String password = student.getPassword();
        String name = student.getFirstName();
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
        String sql = "SELECT * FROM students";
        ResultSet rs = connectionToDB.executeQuery(sql);

        ArrayList<StudentUser> students = new ArrayList<>();
        while(rs.next())
        {
            String studentID = rs.getString("studentID");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String password = rs.getString("password");
            String email = rs.getString("email");
            int day = Integer.parseInt(rs.getString("birthday").substring(8, 10));
            int month = Integer.parseInt(rs.getString("birthday").substring(5, 7));
            int year = Integer.parseInt(rs.getString("birthday").substring(0, 4));
            StudentUser s = new StudentUser(studentID, firstName, lastName, password, email, day, month, year);
            students.add(s);
        }
        rs.close();
        connectionToDB.closeConnection();
        return students;
    }
}
