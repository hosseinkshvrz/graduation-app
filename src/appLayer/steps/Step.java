package appLayer.steps;


public class Step implements IStep {
    private int stepID;
    private String stepName;
    private int acceptStepID;
    private int rejectStepID;
    private int processID;
    private int departmentID;
    private boolean isFirstStep;

    public Step(String stepName, int acceptStepID, int rejectStepID, int processID, int departmentID, boolean isFirstStep) {
        this.stepName = stepName;
        this.acceptStepID = acceptStepID;
        this.rejectStepID = rejectStepID;
        this.processID = processID;
        this.departmentID = departmentID;
        this.isFirstStep = isFirstStep;
    }

    @Override
    public void setStepID(int id) {
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

    public String getStepName() {
        return stepName;
    }

    public boolean isFirstStep() {
        return isFirstStep;
    }

}
