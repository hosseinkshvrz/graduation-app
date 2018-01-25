package appLayer;

import datalayer.DB_Student;

import java.sql.SQLException;

public class User {
    DB_Student student;
    public User() {
        this.student = new DB_Student();
    }

    public boolean isValidUser (String username, String password) throws SQLException, ClassNotFoundException
    {

        return student.isValidUserLogin(username, password);
    }

    public String getName(String username, String password) {
        return student.getStudentName(username, password);
    }
}
