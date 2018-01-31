package appLayer;


public class Step {
    private int stepID;
    private int acceptStepID;
    private int rejectStepID;
    private int processID;
    private int departmentID;

    public Step(int stepID, int acceptStepID, int rejectStepID, int departmentID) {
        this.stepID = stepID;
        this.acceptStepID = acceptStepID;
        this.rejectStepID = rejectStepID;
        this.departmentID = departmentID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
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
}
