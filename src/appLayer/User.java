package appLayer;

import datalayer.DB_Student;

import java.sql.SQLException;

public class User {
    public User() {

    }

    public boolean isValidUser (String username, String password) throws SQLException, ClassNotFoundException
    {
        DB_Student student = new DB_Student();
        return student.isValidUserLogin(username, password);
    }

    public String getName(String username, String password) {
        DB_Student student = new DB_Student();
        return student.getStudentName(username, password);
    }

    public boolean addStudent(String studentID, String password, String name, String email) {
        DB_Student student = new DB_Student();
        return student.addNewStudentToDB(studentID, password, name, email);
    }
}
