package appLayer.processes;


import appLayer.steps.Step;

import java.util.ArrayList;

public class Process implements IProcess {
    private int processID;
    private String processName;
    private ArrayList<Step> processSteps = new ArrayList<>();
    private Step firstStep;

    public Process(String processName) {
        this.processName = processName;
    }

    public Process(int processID, String processName) {
        this.processID = processID;
        this.processName = processName;
    }

    public void addStep(Step newStep) {
        processSteps.add(newStep);
    }

    public void setFirstStep(Step firstStep) {
        this.firstStep = firstStep;
    }

    public String getName() {
        return processName;
    }

    public ArrayList<Step> getProcessSteps() {
        return processSteps;
    }


    public Step getFirstStep() {
        return firstStep;
    }

    @Override
    public void setID(int id) {
        this.processID = id;
    }

    @Override
    public int getID() {
        return processID;
    }
}
