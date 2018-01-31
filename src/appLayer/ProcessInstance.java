package appLayer;


import java.util.ArrayList;

public class ProcessInstance {
    private int processInstanceID;
    private int processID;
    private ArrayList<StepInstance> stepInstances;

    ProcessInstance(int processID) {
        this.processID = processID;
    }

    public void addStepInstance(StepInstance stepInstance) {
        //Add to DB
        stepInstances.add(stepInstance);

    }

    public int getProcessInstanceID() {
        return processInstanceID;
    }

    public int getProcessID() {
        return processID;
    }
}
