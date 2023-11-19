package servertest;

import java.net.*;
import java.io.*;

public class ChatServer {
	
    private ServerSocket serverSocket;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientHandler(clientSocket).start();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 5000;
        ChatServer server = new ChatServer(port);
        server.start();
    }
}

