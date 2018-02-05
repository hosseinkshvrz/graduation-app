package appLayer.steps;


public class StepInstance implements IStep {
    private int stepInstanceID;
    private int stepID;
    private int processInstanceID;
    private String personnelID;
    private String start;
    private String end;

    public StepInstance(int stepID, int processInstanceID, String personnelID, String start) {
        this.stepID = stepID;
        this.processInstanceID = processInstanceID;
        this.personnelID = personnelID;
        this.start = start;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    @Override
    public void setStepID(int id) {
        this.stepInstanceID = id;
    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getStepID() {
        return stepID;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
