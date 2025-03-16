import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10000;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;

    public ChatClient() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            // Connect to the server
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle incoming messages
            Thread messageReceiver = new Thread(new MessageReceiver());
            messageReceiver.start();

            // Print welcome message and instructions
            System.out.println("Connected to chat server!");
            System.out.println("Type your messages and press Enter to send.");
            System.out.println("Type 'exit' to quit the chat.");
            System.out.println("----------------------------------------");

            // Main loop for sending messages
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        try {
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

    // Inner class to handle incoming messages
    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    System.err.println("Connection to server lost: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
} 