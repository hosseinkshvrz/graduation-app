package appLayer;


public class Step {
    private int stepID;
    private String stepName;
    private int acceptStepID;
    private int rejectStepID;
    private int processID;
    private int departmentID;

    public Step(String stepName, int acceptStepID, int rejectStepID, int processID, int departmentID) {
        this.stepName = stepName;
        this.acceptStepID = acceptStepID;
        this.rejectStepID = rejectStepID;
        this.processID = processID;
        this.departmentID = departmentID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }

    public void setAcceptStepID(int acceptStepID) {
        this.acceptStepID = acceptStepID;
    }

    public void setRejectStepID(int rejectStepID) {
        this.rejectStepID = rejectStepID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getStepID() {
        return stepID;
    }

    public int getAcceptStepID() {
        return acceptStepID;
    }

    public int getRejectStepID() {
        return rejectStepID;
    }

    public int getProcessID() {
        return processID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getStepName() {
        return stepName;
    }
}
