package datalayer.tables.processes;


import appLayer.processes.ProcessInstance;
import java.util.HashMap;

public class ProcessInstanceDatabase extends AbstractProcessDatabase {
    private final String tableName = "process_instance";
    public void addNewProcessInstanceToDB(ProcessInstance processInstance) {
        int processID = processInstance.getProcessID();
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("processID", String.valueOf(processID));
        super.addProcessToDB(processInstance, tableName, parameters);
    }

    public void addStepInstancesToProcessInstance(ProcessInstance processInstance) {
        HashMap<String, String> parameters = new HashMap<>();
        for (int i = 0; i < processInstance.getStepInstances().size(); i++) {
            parameters.put("step" + (i+1), processInstance.getStepInstances().get(i).toString());
        }
        super.addAndFillColumns(processInstance, tableName, parameters, "step_instance", "sInstanceID");
    }
}
