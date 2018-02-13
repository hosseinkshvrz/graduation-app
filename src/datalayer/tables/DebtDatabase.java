package datalayer.tables;

import appLayer.Debt;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DebtDatabase {
    private String tableName = "debt";


    public void addNewDebt(Debt debt) {
        String sql = "INSERT INTO " + tableName + " (studentID, amount, personnelID, stepInstanceID, status, time, description) VALUES "
                + "(" + debt.getStudentID() + ", " + debt.getAmount() + ", " + debt.getPersonnelID()
                + ", " + debt.getStepInstanceID() + ", " + debt.getStatus() + ", " + debt.getDebtTime() + ", " + debt.getDescription() + ");";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        int debtID = de.executeAutoIncrementUpdateQuery(sql);
        debt.setDebtID(debtID);
        de.closeConnection();
    }

    public ArrayList<Debt> getAllStudentDebts(String studentID) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = '" + studentID + "';";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        ArrayList<Debt> debts = new ArrayList<>();
        try {
            while (rs.next()) {
                debts.add(getDebt(rs.getInt("id")));
            }
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debts;
    }

    public Debt getDebt(int debtID) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + debtID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Debt debt = null;
        try {
            rs.next();
            String personnelID = rs.getString("personnelID");
            int stepInstanceID = rs.getInt("stepInstanceID");
            int amount = rs.getInt("amount");
            String studentID = rs.getString("studentID");
            String status = rs.getString("status");
            String time = rs.getString("time");
            String description = rs.getString("description");
            String paymentTime = rs.getString("payTime");
            debt = new Debt(studentID, amount, personnelID, stepInstanceID, status, time, description);
            debt.setPaymentTime(paymentTime);
            debt.setDebtID(debtID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debt;
    }

    public void changeStatus(int debtID, String time) {
        String sql = "UPDATE " + tableName + " SET status = 'paid', payTime = '" + time + "' WHERE id = " + debtID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }
}
