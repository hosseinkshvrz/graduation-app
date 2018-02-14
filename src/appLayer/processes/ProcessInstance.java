package appLayer.processes;


import appLayer.steps.StepInstance;

import java.util.ArrayList;

public class ProcessInstance implements IProcess {
    private int processInstanceID;
    private int processID;
    private ArrayList<StepInstance> stepInstances = new ArrayList<>();

    public ProcessInstance(int processID) {
        this.processID = processID;
    }

    public void addStepInstance(StepInstance newStepInstance) {
        stepInstances.add(newStepInstance);
    }

    public ArrayList<StepInstance> getStepInstances() {
        return stepInstances;
    }

    public int getProcessID() {
        return processID;
    }

    @Override
    public void setID(int id) {
        this.processInstanceID = id;
    }

    @Override
    public int getID() {
        return processInstanceID;
    }
}
