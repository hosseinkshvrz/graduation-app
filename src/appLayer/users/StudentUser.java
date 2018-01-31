package appLayer.users;

public class StudentUser extends User {
    private String studentID;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;

    public StudentUser(String studentID, String firstName, String lastName, String password, String email, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        super(firstName, lastName, password, email);
        this.studentID = studentID;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }
}
