package appLayer;

public class PostRequest {
    private int requestID;
    private String question;
    private String personnelID;
    private int stepInstanceID;
    private String studentID;
    private String response;
    private String questionTime;

    public PostRequest(String question, String personnelID, int stepInstanceID, String studentID, String questionTime) {
        this.question = question;
        this.personnelID = personnelID;
        this.stepInstanceID = stepInstanceID;
        this.studentID = studentID;
        this.questionTime = questionTime;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getQuestion() {
        return question;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public int getStepInstanceID() {
        return stepInstanceID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getResponse() {
        return response;
    }

    public String getQuestionTime() {
        return questionTime;
    }
}
