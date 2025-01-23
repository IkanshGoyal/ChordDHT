package chord;

public class App {
    public static void main(String[] args) {
        ChordNetwork network = new ChordNetwork(16);

        Node node1 = network.addNode("127.0.0.1", 8001);
        Node node2 = network.addNode("127.0.0.1", 8002);
        Node node3 = network.addNode("127.0.0.1", 8003);

        node1.put(HashUtils.hash("key1", 16), "value1");
        System.out.println("Get key1: " + node2.get(HashUtils.hash("key1", 16)));

        network.printState();
    }
}