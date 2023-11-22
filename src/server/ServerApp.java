package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import common.*;

public class ServerApp {
    public static void main(String[] args) {
        // IP주소와 포트를 지정하여 ServerSocket형 객체 생성
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            /* Part I: 서버 내부에서의 동작, 클라이언트는 볼 수 없음 */
            System.out.println(ChatServer.waiting());
            while (true) {
                // serverSocket.accept(): 연결된 클라이언트의 Socket형 객체를 반환
                Socket clientSocket = serverSocket.accept();
                final String CLIENT_IP = clientSocket.getInetAddress().getHostAddress();
                // V HashMap.get(K): HashMap에서 K에 해당하는 V가 없다면 null을 반환
                if (ChatServer.getUserName(CLIENT_IP) == null)
                    // 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
                    ChatServer.addUser(new User(CLIENT_IP));
                // "새 클라이언트 연결: IP주소" 출력
                System.out.println(ChatServer.newClient(CLIENT_IP));

                /*
                 * Part II: 멀티스레딩, 클라이언트 스레드를 ArrayList에 추가하고
                 * ClientThread 클래스의 run()을 수행
                 */
                ServerThread thread = new ServerThread(clientSocket);
                ChatServer.getThreadList().add(thread);
                thread.start();
            }
        } catch (IOException e) {
            // ConnectException 외 IOException에 대해 예외가 발생한 부분의 내용을 표시
            e.printStackTrace();
        }
    }
}
