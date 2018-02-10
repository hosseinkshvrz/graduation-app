package datalayer.tables;


import appLayer.Transaction;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsDatabase {
    private final String tableName = "transactions";

    public void addNewTransaction(Transaction transaction) {
        String studentID = transaction.getStudentID();
        String departmentID = transaction.getDepartmentID();
        int stepInstanceID = transaction.getStepInstanceID();
        int amount = transaction.getAmount();
        String sql = "INSERT INTO " + tableName + " (studentID, departmentID, stepInstanceID, amount) VALUES ('"
                + studentID + "', '" + departmentID + "', " + stepInstanceID + ", " + amount + ")";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        int transactionID = de.executeAutoIncrementUpdateQuery(sql);
        transaction.setID(transactionID);
        de.closeConnection();
    }

    public Transaction getTransaction(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = \"" + id + "\"";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        Transaction transaction = null;
        try {
            rs.next();
            String studentID = rs.getString("studentID");
            String departmentID = rs.getString("departmentID");
            int stepInstanceID = rs.getInt("stepInstanceID");
            int amount = rs.getInt("amount");
            transaction = new Transaction(studentID, departmentID, stepInstanceID, amount);
            transaction.setID(id);
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public ArrayList<Transaction> getTransactions() {
        DatabaseExecutor de = new DatabaseExecutor();
        ArrayList<Integer> transactionIDs = new ArrayList<>();
        ArrayList<Transaction> resultTransactions = new ArrayList<>();
        ResultSet rs;
        try {
            String sql = "SELECT * FROM " + tableName;
            rs = de.executeGetQuery(sql);
            while(rs.next()) {
                transactionIDs.add(rs.getInt("id"));
            }
            rs.close();
            de.closeConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < transactionIDs.size(); i++) {
            resultTransactions.add(getTransaction(transactionIDs.get(i)));
        }
        return resultTransactions;
    }
}
