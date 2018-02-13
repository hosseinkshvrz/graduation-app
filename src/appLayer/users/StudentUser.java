package appLayer.users;

public class StudentUser extends User {
    private String studentID;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private int startedProcessInstanceID;
    private int currentStepInstanceID;

    public StudentUser(String firstName, String lastName, String password, String email, String studentID, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        super(firstName, lastName, password, email);
        this.studentID = studentID;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        startedProcessInstanceID = -1;
        currentStepInstanceID = -1;
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

    public void setStartedProcessInstanceID(int startedProcessInstanceID) {
        this.startedProcessInstanceID = startedProcessInstanceID;
    }

    public void setCurrentStepInstanceID(int currentStepInstanceID) {
        this.currentStepInstanceID = currentStepInstanceID;
    }

    public int getStartedProcessInstanceID() {
        return startedProcessInstanceID;
    }

    public int getCurrentStepInstanceID() {
        return currentStepInstanceID;
    }

    public String getBirthDate() {
        return this.getYearOfBirth() + "-" + this.getMonthOfBirth() + "-" + this.getDayOfBirth();
    }
}
