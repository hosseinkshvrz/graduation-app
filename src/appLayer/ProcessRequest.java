package appLayer;


public class ProcessRequest {
    private String studentID;
    private int processID;
    private String status;

    public ProcessRequest(String studentID, int processID, String status) {
        this.studentID = studentID;
        this.processID = processID;
        this.status = status;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getProcessID() {
        return processID;
    }

    public String getStatus() {
        return status;
    }
}
