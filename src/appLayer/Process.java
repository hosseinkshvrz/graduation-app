package appLayer;


import java.util.ArrayList;

public class Process {
    private int processID;
    private String processName;
    private ArrayList<Step> processSteps = new ArrayList<>();
    private String name;

    public Process(String processName) {
        this.processName = processName;
    }

    public void addStep(Step newStep) {

        processSteps.add(newStep);
    }
    public void addStep(int stepID) {
        //first find step then add to array list
    }

    public int getProcessID() {
        return processID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Step> getProcessSteps() {
        return processSteps;
    }
}
