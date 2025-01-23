package chord;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Node {
	private final int id;
    private final String ipAddress;
    private final int port;
    private Node predecessor;
    private Node successor;
    private final Map<Integer, String> data;
    private final FingerTable fingerTable;
    private final int maxNodes;

    public Node(int id, String ipAddress, int port, int maxNodes) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.port = port;
        this.maxNodes = maxNodes;
        this.data = new ConcurrentHashMap<>();
        this.fingerTable = new FingerTable(this, maxNodes);
        this.predecessor = null;
        this.successor = this;
        fingerTable.initialize();
    }

    public int getId() {
        return id;
    }

    public Node getSuccessor() {
        return successor;
    }

    public void setSuccessor(Node successor) {
        this.successor = successor;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public void join(Node existingNode) {
        if (existingNode != null) {
            this.successor = existingNode.findSuccessor(this.id);
        } else {
            this.successor = this;
            this.predecessor = this;
        }
        fingerTable.initialize();
    }

    public Node findSuccessor(int key) {
        if (key > id && key <= successor.getId()) {
            return successor;
        } else {
            Node closestPrecedingNode = fingerTable.getClosestPrecedingNode(key);
            if (closestPrecedingNode == this) {
                return this;
            }
            return closestPrecedingNode.findSuccessor(key);
        }
    }

    public void stabilize() {
        Node x = successor.getPredecessor();
        if (x != null && isInInterval(x.getId(), this.id, successor.getId())) {
            this.successor = x;
        }
        successor.notify(this);
    }

    public void notify(Node n) {
        if (predecessor == null || isInInterval(n.getId(), predecessor.getId(), this.id)) {
            this.predecessor = n;
        }
    }

    public void fixFingers() {
        fingerTable.update();
    }

    public void storeKey(int key, String value) {
        Node responsibleNode = findSuccessor(key);
        responsibleNode.data.put(key, value);
    }

    public String getValue(int key) {
        Node responsibleNode = findSuccessor(key);
        return responsibleNode.data.getOrDefault(key, null);
    }

    private boolean isInInterval(int key, int start, int end) {
        if (start < end) {
            return key > start && key <= end;
        } else {
            return key > start || key <= end;
        }
    }
    
    public void put(int key, String value) {
        Node responsibleNode = findSuccessor(key);
        responsibleNode.data.put(key, value);
    }

    public String get(int key) {
        Node responsibleNode = findSuccessor(key);
        return responsibleNode.data.getOrDefault(key, "Not Found");
    }

    public void leave() {
        if (successor != this) {
            for (Map.Entry<Integer, String> entry : data.entrySet()) {
                successor.data.put(entry.getKey(), entry.getValue());
            }
        }
        if (predecessor != null) {
            predecessor.successor = successor;
        }
        if (successor != null) {
            successor.predecessor = predecessor;
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "Node " + id;
    }
}