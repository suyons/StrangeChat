package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import common.*;

public class ServerApp {
    public static void main(String[] args) {
        // IP주소와 포트를 지정하여 ServerSocket 객체 생성
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println(ChatServer.waiting());

            while (true) {
                // serverSocket.accept(): 연결된 Socket 객체를 반환
                Socket clientSocket = serverSocket.accept();
                System.out.println(ChatServer.newClient(clientSocket.getInetAddress()));

                // V HashMap.get(K): HashMap에서 K에 해당하는 V 페어가 없다면 null을 반환
                if (ChatServer.getUserName(clientSocket.getInetAddress()) == null)
                    // 1개의 K-V 페어 레코드를 HashMap에 추가
                    ChatServer.addUser(new User(clientSocket.getInetAddress()));

                /*
                 * ■ OutputStream on Network
                 * (1) Server Side:
                 * ● The server uses the input stream (getInputStream())
                 * of the client's socket to read data sent by the client.
                 * ● The server uses the output stream (getOutputStream())
                 * of the client's socket to send data to the client.
                 * (2) Client Side:
                 * ● Conversely, the client uses the input stream
                 * of the server's socket to read data sent by the server.
                 * ● The client uses the output stream of its own socket
                 * to send data to the server.
                 */

                // PrintWriter(OutputStream out, boolean autoFlush)
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        clientSocket.getOutputStream(), StandardCharsets.UTF_8)), true);
                out.println(ChatServer.greeting(clientSocket));
                

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
