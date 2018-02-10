package appLayer;


public class Debt {
    private int debtID;
    private String studentID;
    private int amount;
    private String personnelID;
    private int stepInstanceID;
    private String status;

    public Debt(String studentID, int amount, String personnelID, int stepInstanceID, String status) {
        this.studentID = studentID;
        this.amount = amount;
        this.personnelID = personnelID;
        this.stepInstanceID = stepInstanceID;
        this.status = status;
    }

    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDebtID() {
        return debtID;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getAmount() {
        return amount;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    public String getStatus() {
        return status;
    }
}
