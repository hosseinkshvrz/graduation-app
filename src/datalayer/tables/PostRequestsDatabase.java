package datalayer.tables;

import appLayer.PostRequest;
import datalayer.DatabaseExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostRequestsDatabase {
    private final String tableName = "post_requests";


    public void addNewRequest(PostRequest postRequest) {
        String sql = "INSERT INTO " + tableName + " (question, personnelID, stepInstanceID, studentID, questionTime) VALUES "
                + "('" + postRequest.getQuestion() + "', '" + postRequest.getPersonnelID() + "', " + postRequest.getStepInstanceID()
                + ", '" + postRequest.getStudentID() + "', '" + postRequest.getQuestionTime() + "');";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        int requestID = de.executeAutoIncrementUpdateQuery(sql);
        postRequest.setRequestID(requestID);
        de.closeConnection();
    }

    public PostRequest getPostRequest(int requestID) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + requestID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        PostRequest postRequest = null;
        try {
            rs.next();
            String question = rs.getString("question");
            String personnelID = rs.getString("personnelID");
            int stepInstanceID = rs.getInt("stepInstanceID");
            String studentID = rs.getString("studentID");
            String response = rs.getString("response");
            String questionTime = rs.getString("questionTime");
            postRequest = new PostRequest(question, personnelID, stepInstanceID, studentID, questionTime);
            postRequest.setRequestID(requestID);
            postRequest.setResponse(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postRequest;
    }

    public ArrayList<PostRequest> getAllStudentRequests(String studentID) {
        String sql = "SELECT * FROM " + tableName + " WHERE studentID = '" + studentID + "';";
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        ArrayList<PostRequest> postRequests = new ArrayList<>();
        try {
            while (rs.next()) {
                    postRequests.add(getPostRequest(rs.getInt("id")));
            }
            rs.close();
            de.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postRequests;
    }

    public void setResponse(int requestID, String answer) {
        String sql = "UPDATE " + tableName + " SET response = '" + answer + "' WHERE id = " + requestID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        de.executeUpdateQuery(sql);
        de.closeConnection();
    }

    public PostRequest getPostRequest(String personnelID, String studentID, int stepInstanceID) {
        String sql = "SELECT * FROM " + tableName + " WHERE personnelID = '" + personnelID
                + "' AND studentID = '" + studentID + "' stepInstanceID = " + stepInstanceID;
        System.out.println(sql);
        DatabaseExecutor de = new DatabaseExecutor();
        ResultSet rs = de.executeGetQuery(sql);
        PostRequest postRequest = null;
        try {
            rs.next();
            String question = rs.getString("question");
            int requestID = rs.getInt("id");
            String response = rs.getString("response");
            String questionTime = rs.getString("questionTime");
            postRequest = new PostRequest(question, personnelID, stepInstanceID, studentID, questionTime);
            postRequest.setRequestID(requestID);
            postRequest.setResponse(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postRequest;
    }
}
