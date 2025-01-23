package chord;

import java.util.ArrayList;
import java.util.List;

public class ChordNetwork {
    private final List<Node> nodes;
    private final int maxNodes;

    public ChordNetwork(int maxNodes) {
        this.nodes = new ArrayList<>();
        this.maxNodes = maxNodes;
    }

    public Node addNode(int id) {
        Node newNode = new Node(id, maxNodes);
        if (!nodes.isEmpty()) {
            newNode.join(nodes.get(0));
        }
        nodes.add(newNode);
        for (Node node : nodes) {
            node.stabilize();
        }
        return newNode;
    }

    public void displayNetwork() {
        for (Node node : nodes) {
            System.out.println(node + " -> Successor: " + node.getSuccessor() + ", Predecessor: " + node.getPredecessor());
        }
    }
}