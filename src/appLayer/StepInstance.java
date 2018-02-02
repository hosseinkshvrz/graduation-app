package appLayer;


public class StepInstance {
    private int stepInstanceID;
    private int stepID;
    private int acceptStepInstanceID;
    private int rejectStepInstanceID;
    private int processInstanceID;
    private String personnelID;
    private String start;
    private String end;

    public StepInstance(int stepID, int acceptStepInstanceID, int rejectStepInstanceID, int processInstanceID,
                        String personnelID, String start) {
        this.stepID = stepID;
        this.acceptStepInstanceID = acceptStepInstanceID;
        this.rejectStepInstanceID = rejectStepInstanceID;
        this.processInstanceID = processInstanceID;
        this.personnelID = personnelID;
        this.start = start;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    public void setStepInstanceID(int stepInstanceID) {
        this.stepInstanceID = stepInstanceID;
    }

    public int getAcceptStepInstanceID() {
        return acceptStepInstanceID;
    }

    public void setAcceptStepInstanceID(int acceptStepInstanceID) {
        this.acceptStepInstanceID = acceptStepInstanceID;
    }

    public int getRejectStepInstanceID() {
        return rejectStepInstanceID;
    }

    public void setRejectStepInstanceID(int rejectStepInstanceID) {
        this.rejectStepInstanceID = rejectStepInstanceID;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(String personnelID) {
        this.personnelID = personnelID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getStepID() {
        return stepID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }
}
