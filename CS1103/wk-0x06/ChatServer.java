import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 10000;
    private static HashSet<PrintWriter> clients = new HashSet<>();
    private static HashMap<Socket, String> clientUsernames = new HashMap<>();
    private static int userIdCounter = 1;

    public static void main(String[] args) {
        System.out.println("Chat Server is running...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                
                // Create a new thread for each client
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Inner class to handle client connections
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userId;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.userId = "User" + userIdCounter++;
        }

        public void run() {
            try {
                // Set up input and output streams
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Add this client to the set of all clients
                synchronized (clients) {
                    clients.add(out);
                    clientUsernames.put(socket, userId);
                }

                // Broadcast that a new user has joined
                broadcast(userId + " has joined the chat!");

                // Process messages from this client
                String message;
                while ((message = in.readLine()) != null) {
                    if (!message.isEmpty()) {
                        broadcast(userId + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    // Remove this client and close connections
                    synchronized (clients) {
                        clients.remove(out);
                        clientUsernames.remove(socket);
                    }
                    broadcast(userId + " has left the chat.");
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client connection: " + e.getMessage());
                }
            }
        }

        // Broadcast a message to all connected clients
        private void broadcast(String message) {
            synchronized (clients) {
                System.out.println(message); // Server-side logging
                for (PrintWriter client : clients) {
                    client.println(message);
                }
            }
        }
    }
} 