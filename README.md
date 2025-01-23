# ChordDHT

A Java-based implementation of the Chord Distributed Hash Table (DHT) protocol with a modern Swing-based UI. The application allows users to create a distributed peer-to-peer system for key-value storage and retrieval. Nodes in the network dynamically handle key distribution and updates as nodes join or leave the system.

## Features

- **Fully functional Chord DHT implementation.**
- **Distributed key-value storage using consistent hashing.**
- **Support for dynamic node creation, joining, and leaving.**
- **Modern, interactive Swing-based UI for issuing commands and visualizing the network.**
- **Automatic updates to the finger table, successor, and predecessor pointers for efficient lookups.**

## Technologies Used

- **Java**: Core logic and backend implementation.
- **Swing**: Modern graphical user interface.
- **SHA-1 Hashing**: For consistent hashing of keys and node identifiers.

## Project Structure

```
ChordDHT/
├── src/
│   ├── chord/
│   │   ├── Node.java              # Node logic
│   │   ├── FingerTable.java       # Routing table implementation
│   │   ├── ChordNetwork.java      # Network management
│   │   ├── HashUtils.java         # Hashing utility
│   ├── ui/
│   │   ├── ChordUI.java           # Modern Swing-based UI
│   ├── App.java                   # Entry point
├── README.md
├── build.gradle                   # Gradle build configuration
├── .gitignore
```

## Setup and Installation

### Prerequisites

- **Java Development Kit (JDK) version 11 or later.**
- **A Java IDE such as IntelliJ IDEA, Eclipse, or VS Code.**
- **Gradle (Optional: You can use the included build.gradle file).**

### Steps to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/ikanshgoyal/ChordDHT.git
   cd ChordDHT
   ```

2. Open the project in your preferred Java IDE.

3. Run the `App.java` file for command-line interaction or `ChordUI.java` for the graphical user interface.

## Commands

Here are the commands supported by the application:

| Command     | Arguments        | Description                                      |
|-------------|------------------|--------------------------------------------------|
| `create`    | `<ip> <port>`    | Creates a new node in the network.               |
| `put`       | `<key> <value>`  | Inserts a key-value pair into the network.       |
| `get`       | `<key>`          | Retrieves the value for the given key.           |
| `printstate`| None             | Displays the current state of the network.       |

## Detailed Command Examples

### 1. `create <ip> <port>`

Creates a new node in the network.

```bash
create 127.0.0.1 8001
```

**Output:**

```
Node created at IP: 127.0.0.1, Port: 8001
```

### 2. `put <key> <value>`

Stores a key-value pair in the network.

```bash
put five 5
```

**Output:**

```
Inserted key: five with value: 5
```

### 3. `get <key>`

Retrieves the value associated with the given key.

```bash
get five
```

**Output:**

```
Found key: five with value: 5
```

If the key is not found:

```
Key five not found
```

### 4. `printstate`

Displays the current state of the Chord network, including node IDs, IP addresses, and ports.

```bash
printstate
```

**Example Output:**

```
Current Chord State:
Node ID: 1, IP: 127.0.0.1, Port: 8001
Node ID: 9, IP: 127.0.0.1, Port: 8002
```

## How It Works

1. **Node Creation:**
   - Use the `create` command to start nodes with unique IP and port combinations.
   - Nodes dynamically join the network and update their routing information.

2. **Key-Value Storage:**
   - Use `put` to insert data into the distributed hash table.
   - The system hashes the key and stores it in the appropriate node.

3. **Data Retrieval:**
   - Use `get` to retrieve the value for a given key. The system locates the responsible node and fetches the data.

4. **Network State:**
   - Use `printstate` to view the details of all nodes in the network.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add your feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.
