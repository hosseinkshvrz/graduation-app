package appLayer.steps;


public class StepInstance implements IStep {
    private int stepInstanceID;
    private int stepID;
    private int processInstanceID;
    private String personnelID;
    private String start;
    private String end;
    private String studentID;
    private String result;

    public StepInstance(int stepID, int processInstanceID, String personnelID, String start, String studentID, String result) {
        this.stepID = stepID;
        this.processInstanceID = processInstanceID;
        this.personnelID = personnelID;
        this.start = start;
        this.studentID = studentID;
        this.result = result;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    @Override
    public void setID(int id) {
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

    public String getStudentID() {
        return studentID;
    }

    public String getResult() {
        return result;
    }
}
