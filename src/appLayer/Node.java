package appLayer;

public class Node {
    private int id;
    private int nextStepID;

    public Node(int id, int nextStepID) {
        this.id = id;
        this.nextStepID = nextStepID;
    }

    public int getId() {
        return id;
    }

    public int getNextStepID() {
        return nextStepID;
    }
}
