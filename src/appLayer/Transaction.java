package appLayer;

public class Transaction {
    private int id;
    private String studentID;
    private String departmentID;
    private int stepInstanceID;
    private int amount;

    public Transaction(String studentID, String departmentID, int stepInstanceID, int amount) {
        this.studentID = studentID;
        this.departmentID = departmentID;
        this.stepInstanceID = stepInstanceID;
        this.amount = amount;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    public int getAmount() {
        return amount;
    }
}
