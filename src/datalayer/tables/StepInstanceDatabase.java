package datalayer.tables;


import appLayer.StepInstance;
import datalayer.DatabaseExecutor;

public class StepInstanceDatabase {
    private final String tableName = "step_instance";
    public void addNewStepInstanceToDB(StepInstance stepInstance) {
        DatabaseExecutor de = new DatabaseExecutor();
        String sql = "INSERT INTO " + tableName + " (stepID, pInstanceID, personnelID, start)" +
                " VALUE ('" + stepInstance.getStepID() + "', " + stepInstance.getProcessInstanceID() + ", "
                + stepInstance.getPersonnelID() + ", " + stepInstance.getStart() + ");";
        System.out.println(sql);
        int stepInstanceID = de.executeAutoIncrementUpdateQuery(sql);
        stepInstance.setStepInstanceID(stepInstanceID);
        de.closeConnection();
    }
}
