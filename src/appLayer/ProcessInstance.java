package appLayer;


import java.util.ArrayList;

public class ProcessInstance {
    private int processInstanceID;
    private int processID;
    private ArrayList<StepInstance> stepInstances;

    public ProcessInstance(int processID) {
        this.processID = processID;
    }

    public void setProcessInstanceID(int processInstanceID) {
        this.processInstanceID = processInstanceID;
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

    public int getProcessInstanceID() {
        return processInstanceID;
    }
}
