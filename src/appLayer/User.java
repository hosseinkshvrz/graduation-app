package appLayer;

import datalayer.DB_Student;

import java.sql.SQLException;

public class User {
    public boolean isValidUser (String username, String password) throws SQLException, ClassNotFoundException
    {
        DB_Student student = new DB_Student();

        return student.isValidUserLogin(username, password);
    }
}
