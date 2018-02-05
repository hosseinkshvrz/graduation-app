package datalayer.tables.steps;


import appLayer.steps.StepInstance;

import java.util.HashMap;

public class StepInstanceDatabase extends AbstractStepDatabase {
    private final String tableName = "step_instance";
    public void addNewStepInstanceToDB(StepInstance stepInstance) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("stepID", String.valueOf(stepInstance.getStepID()));
        parameters.put("pInstanceID", String.valueOf(stepInstance.getProcessInstanceID()));
        parameters.put("personnelID", String.valueOf(stepInstance.getPersonnelID()));
        parameters.put("start", stepInstance.getStart());
        super.addStepToDB(stepInstance, tableName, parameters);
    }
}
