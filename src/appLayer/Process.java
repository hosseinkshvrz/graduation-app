package appLayer;


import java.util.ArrayList;

public class Process {
    private int processID;
    private String processName;
    private ArrayList<Step> processSteps = new ArrayList<>();

    public Process(String processName) {
        this.processName = processName;
    }

    public void addStep(Step newStep) {
        processSteps.add(newStep);
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

    public void addStep(int stepID) {

    }
}
