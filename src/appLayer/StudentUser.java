package appLayer;

public class StudentUser {
    private String studentID;
    private String name;
    private String password;
    private String email;

    public StudentUser(String studentID, String name, String password, String email) {
        this.studentID = studentID;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
