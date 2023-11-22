package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ConcurrentModificationException;

import common.*;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final String CLIENT_IP;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        CLIENT_IP = clientSocket.getInetAddress().getHostAddress();
    }

    @Override
    public void run() {
        try {
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
            reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), StandardCharsets.UTF_8)),
                    true);

            // Server continuously listens for messages from the client
            while (true) {
                // 클라이언트 접속 시 "OOOO님께서 입장하셨습니다." 출력
                try {
                    if (clientSocket != null) {
                        broadcastMsg(ChatServer.clientJoined(CLIENT_IP));
                    }
                } catch (ConcurrentModificationException e) {
                    break;
                }

                // 클라이언트로부터 받은 메시지가 null 아니라면 이를 서버에서 처리하고
                // 모든 클라이언트에게 broadcast
                String content;
                while ((content = reader.readLine()) != null) {
                    Chat chat = ChatServer.clientsChat(CLIENT_IP, content);
                    System.out.println(chat);
                    broadcastMsg(chat);
                }
            }
        } catch (SocketException e) {
            /*
             * 클라이언트 소켓이 close() 메서드를 통해 정상적으로 종료된 상황이 아닌데
             * 데이터 전송이 안 될 때 SocketException이 발생한다.
             * SocketException 예외 처리 후 해당 스레드는 종료되며, ServerSocket이
             * 완전히 종료되는 것이 아니라 1개 클라이언트에 대한 스레드만 종료되는
             * 것이므로 다른 클라이언트와의 연결은 그대로 유지된다.
             */
            ChatServer.getThreadList().remove(this);
            broadcastMsg(ChatServer.clientLeft(CLIENT_IP));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastMsg(Chat chat) {
        if (chat != null)
            for (ServerThread thread : ChatServer.getThreadList())
                thread.writer.println(chat.toString());
    }
}