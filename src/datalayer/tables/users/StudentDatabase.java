package datalayer.tables.users;

import appLayer.users.StudentUser;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDatabase extends AbstractUserDatabase {
    private final String tableName = "students";

    public boolean isValidStudentLogin(String studentID, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = \"" + studentID + "\" AND password = \"" + password + "\"";
        System.out.println(sql);
        try {
            return checkUserExistenceWithDatabase(sql);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public StudentUser getUser(String userID) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = \"" + userID + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        StudentUser student = null;
        try {
            rs.next();
            String id = rs.getString("studentID");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String pass = rs.getString("password");
            String mail = rs.getString("email");
            String status = rs.getString("status");
            int day = Integer.parseInt(rs.getString("birthday").split("-")[2]);
            int month = Integer.parseInt(rs.getString("birthday").split("-")[1]);
            int year = Integer.parseInt(rs.getString("birthday").split("-")[0]);
            student = new StudentUser(id, firstName, lastName, pass, mail, day, month, year, status);
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
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

        String sql = "INSERT " + tableName + " (studentID, firstname, lastname, password, email, birthday) VALUES ('"
                + studentID + "', '" + firstName + "', '" + lastName + "', '" + password + "', '" + email + "', '"
                + year + "-" + month + "-" + day + "')";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        addIsDone = de.executeUpdateQuery(sql);

        de.closeConnection();
        return addIsDone;
    }

    public ArrayList<StudentUser> getListOfAllStudents() throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);

        ArrayList<StudentUser> students = new ArrayList<>();
        while(rs.next())
        {
            String studentID = rs.getString("studentID");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastname");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String status = rs.getString("status");
            int day = Integer.parseInt(rs.getString("birthday").split("-")[2]);
            int month = Integer.parseInt(rs.getString("birthday").split("-")[1]);
            int year = Integer.parseInt(rs.getString("birthday").split("-")[0]);
            StudentUser s = new StudentUser(studentID, firstName, lastName, password, email, day, month, year, status);
            students.add(s);
        }
        rs.close();
        de.closeConnection();
        return students;
    }
}
