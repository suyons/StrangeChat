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
import java.util.Iterator;

import common.*;

// Thread implements Runnable → 멀티스레드 구현 가능
public class ServerThread extends Thread {
    // 소켓, 네트워크 입출력 스트림 정의
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    // 클라이언트의 IP 주소
    private final String CLIENT_IP;

    // 생성자 재정의: 상수 String CLIENT_IP에 클라이언트 IP주소를 대입
    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        CLIENT_IP = clientSocket.getInetAddress().getHostAddress();
    }

    // 멀티스레드로 실행할 내용 = 서버 동작의 핵심부
    @Override
    public void run() {
        try {
            /*
             * ■ 네트워크에서의 입력(reader) / 출력(writer) 스트림
             * (1) 서버 측
             * ● 클라이언트 소켓의 입력 스트림: 클라이언트 → 서버
             * ● 클라이언트 소켓의 출력 스트림: 서버 → 클라이언트
             * (2) 클라이언트 측
             * ● 서버 소켓의 입력 스트림: 서버 → 클라이언트
             * ● 서버 소켓의 출력 스트림: 클라이언트 → 서버
             */
            reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream(), StandardCharsets.UTF_8));
            writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), StandardCharsets.UTF_8)),
                    true);

            // 클라이언트가 새로 접속할 때 이전 대화내용을 전송
            showPreviousMsg();

            // 서버 ↔ 클라이언트 양방향 메시지 교환을 지속 수행
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

    // 매개변수로 넣은 chat의 내용을 모든 클라이언트에게 전송
    private void broadcastMsg(Chat chat) {
        if (chat != null)
            for (ServerThread thread : ChatServer.getThreadList())
                thread.writer.println(chat.toString());
    }

    // 클라이언트 신규 입장 시 이전 대화 내용을 전송
    private void showPreviousMsg() {
        Iterator<Long> ir = ChatServer.getIterator();
        while (ir.hasNext()) {
            Long timestamp = ir.next();
            String content = ChatServer.getChatContents(timestamp);
            writer.println(content);
        }
    }
}