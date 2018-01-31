package appLayer;


import java.util.ArrayList;

public class Process {
    private int processID;
    private ArrayList<Step> processSteps = new ArrayList<>();

    public Process() {
    }


    public void addStep(Step newStep) {
        //add to database
        processSteps.add(newStep);
    }

    public int getProcessID() {
        return processID;
    }
}
