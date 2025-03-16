# Online Chat Application

A simple text-based chat application implemented in Java using socket programming. This application allows multiple users to connect to a central server and exchange messages in real-time.

## Features

- Multi-client support
- Text-based user interface
- Real-time message broadcasting
- User join/leave notifications
- Unique user IDs for each client

## Requirements

- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt

## How to Run

1. First, compile both Java files:

```bash
javac ChatServer.java
javac ChatClient.java
```

2. Start the server:

```bash
java ChatServer
```

3. In separate terminal windows, start multiple clients:

```bash
java ChatClient
```

## Usage Instructions

1. The server must be running before any clients can connect.
2. Each client will automatically connect to the server running on localhost:5000.
3. Once connected, you can start typing messages and press Enter to send.
4. To exit the chat, type 'exit' and press Enter.

## Implementation Details

### Server (ChatServer.java)

- Listens for incoming connections on port 5000
- Creates a new thread for each connected client
- Maintains a list of all connected clients
- Broadcasts messages to all connected clients

### Client (ChatClient.java)

- Connects to the server using sockets
- Uses separate threads for sending and receiving messages
- Provides a simple text-based interface
- Handles disconnection gracefully

## Error Handling

The application includes basic error handling for:

- Connection failures
- Client disconnections
- Invalid inputs
- Server shutdown

## Screenshots

To add screenshots of your running application:

1. Run the server and multiple clients
2. Take screenshots showing the interaction between clients
3. Add the screenshots to this directory
4. Update this README to include the screenshot files
