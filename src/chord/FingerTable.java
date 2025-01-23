package chord;

public class FingerTable {
    private final Node node;
    private final int maxNodes;
    private final Finger[] fingers;

    public FingerTable(Node node, int maxNodes) {
        this.node = node;
        this.maxNodes = maxNodes;
        this.fingers = new Finger[maxNodes];
    }

    public void initialize() {
        for (int i = 0; i < maxNodes; i++) {
            int start = (node.getId() + (1 << i)) % maxNodes;
            fingers[i] = new Finger(start, node); 
        }
    }

    public Node getClosestPrecedingNode(int key) {
        for (int i = maxNodes - 1; i >= 0; i--) {
            if (fingers[i].node != null && isInInterval(fingers[i].node.getId(), node.getId(), key)) {
                return fingers[i].node;
            }
        }
        return node;
    }

    public void update() {
        for (int i = 0; i < maxNodes; i++) {
            fingers[i].node = node.findSuccessor(fingers[i].start);
        }
    }

    private boolean isInInterval(int key, int start, int end) {
        if (start < end) {
            return key > start && key <= end;
        } else {
            return key > start || key <= end;
        }
    }

    private static class Finger {
        int start;
        Node node;

        Finger(int start, Node node) {
            this.start = start;
            this.node = node;
        }
    }
}