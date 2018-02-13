package datalayer.tables.users;

import appLayer.users.StudentUser;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDatabase extends AbstractUserDatabase {
    private final String tableName = "students";

    public boolean isValidStudentLogin(String studentID, String password) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = '" + studentID + "' AND password = '" + password + "';";
        System.out.println(sql);
        try {
            return checkUserExistenceWithDatabase(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public StudentUser getUser(String userID) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = '" + userID + "'";
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
            int processInstanceID = rs.getInt("pInstanceID");
            if (rs.wasNull()) {
                processInstanceID = -1;
            }
            int stepInstanceID = rs.getInt("csInstanceID");
            if (rs.wasNull()) {
                stepInstanceID = -1;
            }
            int day = Integer.parseInt(rs.getString("birthday").split("-")[2]);
            int month = Integer.parseInt(rs.getString("birthday").split("-")[1]);
            int year = Integer.parseInt(rs.getString("birthday").split("-")[0]);
            student = new StudentUser(firstName, lastName, pass, mail, id, day, month, year);
            student.setStartedProcessInstanceID(processInstanceID);
            student.setCurrentStepInstanceID(stepInstanceID);
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

        String sql = "INSERT INTO " + tableName + " (studentID, firstname, lastname, password, email, birthday) VALUES ('"
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
            students.add(getUser(rs.getString("studentID")));
        }
        rs.close();
        de.closeConnection();
        return students;
    }

    public void changeCurrentState(String studentID, int pInstanceID, int stepInstanceID) {
        String sql = "UPDATE " + tableName + " SET pInstanceID = " + pInstanceID
                    + ", csInstanceID = " + stepInstanceID + " WHERE studentID = '" + studentID +"';";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }

    public StudentUser getStudentInStepInstance(int stepInstanceID) {
        String sql = "SELECT * FROM " + tableName + " WHERE csInstanceID = " + stepInstanceID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        StudentUser student = null;
        try {
            rs.next();
            student = getUser(rs.getString("studentID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void editStudent(StudentUser student) {
        String sql = "UPDATE " + tableName + " SET firstname = '" + student.getFirstName() + "'"
                + ", lastname = '" + student.getLastName() + "', password = '" + student.getPassword() + "'"
                + ", email = '" + student.getEmail() + "'"
                + ", birthday = '" + student.getYearOfBirth() + "-" + student.getMonthOfBirth() + "-" + student.getDayOfBirth() + "'"
                + " WHERE studentID = '" + student.getStudentID() +"';";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }
}
