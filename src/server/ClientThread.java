package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import common.Chat;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientThread(Socket clientSocket) {
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
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            // Server continuously listens for messages from the client
            while (true) {
                // 클라이언트 접속 시 "OOOO님 반갑습니다!" 출력
                broadcastMsg(ChatServer.greeting(clientSocket));

                // 클라이언트로부터 받은 메시지를 서버에서 출력하고, 클라이언트에게도 송출
                String content;
                while ((content = reader.readLine()) != null) {
                    Chat chat = ChatServer.clientsChat(clientSocket.getInetAddress(), content);
                    System.out.println(chat);
                    broadcastMsg(chat);
                }
            }
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
        for (ClientThread thread : ChatServer.getThreadList()) {
            try {
                thread.writer.write(chat.toString());
                thread.writer.flush();
            } catch (IOException e) {
                System.out.println("broadcastMsg() 메서드 예외 발생");
                e.printStackTrace();
            }
        }
    }
}
