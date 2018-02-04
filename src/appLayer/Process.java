package appLayer;


import java.util.ArrayList;

public class Process {
    private int processID;
    private String status;
    private String processName;
    private ArrayList<Step> processSteps = new ArrayList<>();
    private Step firstStep;

    public Process(String processName) {
        this.processName = processName;
    }

    public Process(int processID, String processName, String status) {
        this.processID = processID;
        this.processName = processName;
        this.status = status;
    }

    public void addStep(Step newStep) {
        processSteps.add(newStep);
    }

    public void setFirstStep(Step firstStep) {
        this.firstStep = firstStep;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getProcessID() {
        return processID;
    }

    public String getName() {
        return processName;
    }

    public ArrayList<Step> getProcessSteps() {
        return processSteps;
    }

    public String getStatus() {
        return status;
    }

    public Step getFirstStep() {
        return firstStep;
    }
}
