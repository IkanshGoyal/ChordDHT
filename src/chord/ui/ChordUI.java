package chord.ui;

import chord.ChordNetwork;
import chord.HashUtils;
import chord.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChordUI extends JFrame {
    private final JTextArea output;
    private final JTextField commandField;
    private final ChordNetwork network;

    public ChordUI() {
        setTitle("Chord DHT");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        network = new ChordNetwork(16);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);

        commandField = new JTextField();
        JButton executeButton = new JButton("Execute");

        executeButton.addActionListener(this::handleCommand);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(commandField, BorderLayout.CENTER);
        bottomPanel.add(executeButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleCommand(ActionEvent e) {
        String command = commandField.getText().trim();
        commandField.setText("");
        processCommand(command);
    }

    private void processCommand(String command) {
        try {
            String[] parts = command.split("\\s+");
            if (parts.length == 0) {
                output.append("Invalid command\n");
                return;
            }

            switch (parts[0].toLowerCase()) {
                case "create":
                    if (parts.length != 3) {
                        output.append("Usage: create <ip> <port>\n");
                    } else {
                        String ip = parts[1];
                        int port = Integer.parseInt(parts[2]);
                        network.addNode(ip, port);
                        output.append("Node created at IP: " + ip + ", Port: " + port + "\n");
                    }
                    break;

                case "put":
                    if (parts.length != 3) {
                        output.append("Usage: put <key> <value>\n");
                    } else if (network.getNodes().isEmpty()) {
                        output.append("No nodes available. Please create a node first using the 'create' command.\n");
                    } else {
                        String key = parts[1];
                        String value = parts[2];
                        int hashedKey = HashUtils.hash(key, 16);
                        network.getNodes().get(0).put(hashedKey, value); 
                        output.append("Inserted key: " + key + " with value: " + value + "\n");
                    }
                    break;

                case "get":
                    if (parts.length != 2) {
                        output.append("Usage: get <key>\n");
                    } else if (network.getNodes().isEmpty()) {
                        output.append("No nodes available. Please create a node first using the 'create' command.\n");
                    } else {
                        String key = parts[1];
                        int hashedKey = HashUtils.hash(key, 16);
                        String result = network.getNodes().get(0).get(hashedKey);
                        output.append(result.equals("Not Found")
                                ? "Key " + key + " not found\n"
                                : "Found key: " + key + " with value: " + result + "\n");
                    }
                    break;

                case "printstate":
                    if (network.getNodes().isEmpty()) {
                        output.append("No nodes in the network.\n");
                    } else {
                        output.append("Current Chord State:\n");
                        for (Node node : network.getNodes()) {
                            output.append("Node ID: " + node.getId() + ", IP: " + node.getIpAddress() + ", Port: " + node.getPort() + "\n");
                        }
                    }
                    break;

                default:
                    output.append("Unknown command: " + parts[0] + "\n");
            }
        } catch (Exception ex) {
            output.append("Error processing command: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChordUI ui = new ChordUI();
            ui.setVisible(true);
        });
    }
}