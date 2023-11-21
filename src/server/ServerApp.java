package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import common.Constants;
import common.User;

public class ServerApp {
    public static void main(String[] args) {
        // IP주소와 포트를 지정하여 ServerSocket형 객체 생성
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            /* Part I: 서버 내부에서의 동작, 클라이언트는 볼 수 없음 */
            System.out.println(ChatServer.waiting());
            while (true) {
                // serverSocket.accept(): 연결된 클라이언트의 Socket형 객체를 반환
                Socket clientSocket = serverSocket.accept();
                
                // V HashMap.get(K): HashMap에서 K에 해당하는 V 페어가 없다면 null을 반환
                if (ChatServer.getUserName(clientSocket.getInetAddress()) == null)
                    // 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
                    ChatServer.addUser(new User(clientSocket.getInetAddress()));
                // "새 클라이언트 연결: IP주소" 출력
                System.out.println(ChatServer.newClient(clientSocket.getInetAddress()));

                /*
                 * Part II: 멀티스레딩, 클라이언트 스레드를 ArrayList에 추가하고
                 * ClientThread 클래스의 run()을 수행
                 */
                ServerThread thread = new ServerThread(clientSocket);
                ChatServer.getThreadList().add(thread);
                thread.start();
            }
        } catch (SocketException e) {
            /*
             * 클라이언트 소켓이 close() 메서드를 통해 정상적으로 종료된 상황이 아닌데
             * 데이터 전송이 안 될 때 SocketException이 발생한다.
             * SocketException 예외 처리 후 해당 스레드는 종료되며, ServerSocket이
             * 완전히 종료되는 것이 아니라 1개 클라이언트에 대한 스레드만 종료되는
             * 것이므로 다른 클라이언트와의 연결은 그대로 유지된다.
             */
            System.out.println(Constants.SYSTEM_NAME + "클라이언트가 연결을 종료했습니다.");
        } catch (IOException e) {
            // ConnectException 외 IOException에 대해 예외가 발생한 부분의 내용을 표시
            e.printStackTrace();
        }
    }
}
