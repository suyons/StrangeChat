package swingserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import common.*;

public class SwingServerThread extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SwingServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
                            clientSocket.getOutputStream(), StandardCharsets.UTF_8)), true);

            // Server continuously listens for messages from the client
            while (true) {
                // 클라이언트 접속 시 "OOOO님 반갑습니다!" 출력
                broadcastMsg(ChatServer.greeting(clientSocket));

                // 클라이언트로부터 받은 메시지가 null 아니라면 이를 서버에서 처리하고
                // 모든 클라이언트에게 broadcast
                String content;
                while ((content = reader.readLine()) != null) {
                    Chat chat = ChatServer.clientsChat(clientSocket.getInetAddress(), content);
                    System.out.println(chat);
                    broadcastMsg(chat);
                }
            }
        } catch (SocketException e) {
            System.out.println(Constants.SYSTEM_NAME + "클라이언트가 채팅을 종료하였습니다.");
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
        for (SwingServerThread thread : ChatServer.getThreadList()) {
            try {
                thread.writer.println(chat.toString());
            } catch (Exception e) {
                System.out.println("broadcastMsg() 메서드 예외 발생");
                e.printStackTrace();
            }
        }
    }
}
