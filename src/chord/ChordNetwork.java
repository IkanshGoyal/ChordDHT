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

    public Node addNode(String ip, int port) {
        int id = HashUtils.hash(ip + ":" + port, maxNodes);
        Node newNode = new Node(id, ip, port, maxNodes);
        if (!nodes.isEmpty()) {
            newNode.join(nodes.get(0));
        }
        nodes.add(newNode);
        for (Node node : nodes) {
            node.stabilize();
        }
        return newNode;
    }

    public void removeNode(Node node) {
        nodes.remove(node);
        node.leave();
    }

    public void printState() {
        for (Node node : nodes) {
            System.out.println("Node ID: " + node.getId() + ", IP: " + node.getIpAddress() + ", Port: " + node.getPort());
        }
    }
}