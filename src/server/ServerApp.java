package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle the client connection in a separate thread or class
                // (e.g., create a ClientHandler class to manage client interactions)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
