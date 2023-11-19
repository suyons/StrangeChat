package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import common.*;

public class ServerApp {
    public static void main(String[] args) {
        // Creates a server socket, bound to the specified port.
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println("Server is listening on port " + Constants.SERVER_PORT);

            while (true) {
                // Listens for a connection to be made to this socket and accepts it.
                // The method blocks until a connection is made.
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // V HashMap.get() returns null if there is no match on the given K.
                // Then it adds one K-V pair on userMap
                if(ChatServer.getUserName(clientSocket.getInetAddress()) == null)
                    ChatServer.addUser(new User(clientSocket.getInetAddress()));
                
                System.out.println(ChatServer.getUserName(clientSocket.getInetAddress()) + "님 반갑습니다!");    
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
