package server;

import java.io.IOException;
import java.io.PrintWriter;
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
                Socket clientSocket = serverSocket.accept();
                System.out.println("새 클라이언트 연결: " + clientSocket.getInetAddress());

                // V HashMap.get() returns null if there is no match on the given K.                
                if(ChatServer.getUserName(clientSocket.getInetAddress()) == null)
                    // Then it adds one K-V pair on userMap
                    ChatServer.addUser(new User(clientSocket.getInetAddress()));

                /* ■ OutputStream on Network
                 * (1) Server Side:
                 * ● The server uses the input stream (getInputStream())
                 *   of the client's socket to read data sent by the client.
                 * ● The server uses the output stream (getOutputStream())
                 *   of the client's socket to send data to the client.
                 * (2) Client Side:
                 * ● Conversely, the client uses the input stream
                 *   of the server's socket to read data sent by the server.
                 * ● The client uses the output stream of its own socket
                 *   to send data to the server.
                 */

                // PrintWriter(OutputStream out, boolean autoFlush)
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(ChatServer.greeting(clientSocket));
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
