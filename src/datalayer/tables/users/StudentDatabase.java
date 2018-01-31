package datalayer.tables.users;

import appLayer.StudentUser;
import datalayer.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDatabase extends UserDatabase {
    private final String tableName = "students";

    public boolean isValidStudentLogin(String studentID, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
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

    public StudentUser getUser(String userID) throws SQLException {
        String sql = "SELECT * FROM students WHERE studentID = \"" + userID + "\"";
        System.out.println(sql);
        DatabaseConnector connectionToDB = new DatabaseConnector();
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
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String email = student.getEmail();
        int day = student.getDayOfBirth();
        int month = student.getMonthOfBirth();
        int year = student.getYearOfBirth();

        String sql = "INSERT INTO students (studentID, firstname, lastname, password, email, birthday) VALUES ('"
                + studentID + "', '" + firstName + "', '" + lastName + "', '" + password + "', '" + email + "', '"
                + year + "-" + month + "-" + day + "')";
        System.out.println(sql);
        DatabaseConnector connectionToDB = new DatabaseConnector();
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
        DatabaseConnector connectionToDB = new DatabaseConnector();
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
