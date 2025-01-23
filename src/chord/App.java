package chord;

public class App {
    public static void main(String[] args) {
        int maxNodes = 8; 
        ChordNetwork network = new ChordNetwork(maxNodes);

        System.out.println("Adding nodes...");
        Node node1 = network.addNode(1);
        Node node3 = network.addNode(3);
        Node node7 = network.addNode(7);

        network.displayNetwork();

        System.out.println("\nStoring keys...");
        int key1 = HashUtils.hash("key1", maxNodes);
        node1.storeKey(key1, "Value1");

        System.out.println("\nRetrieving key...");
        System.out.println("Key1: " + node3.getValue(key1));
    }
}