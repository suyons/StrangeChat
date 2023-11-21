package csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import common.*;

public class ServerApp {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		// IP주소와 포트를 지정하여 ServerSocket 객체 생성
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT);
			System.out.println(ChatServer.waiting());

			while (true) {
				// serverSocket.accept(): 연결된 Socket 객체를 반환
				clientSocket = serverSocket.accept();
				// V HashMap.get(K): HashMap에서 K에 해당하는 V 페어가 없다면 null을 반환

				if (ChatServer.getUserName(clientSocket.getInetAddress()) == null)
					// 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
					ChatServer.addUser(new User(clientSocket.getInetAddress()));
				// User.csv 파일에 유저 아이피주소, 닉네임 넣기
				ChatServer.addUserCsv(clientSocket.getInetAddress());
				// "새 클라이언트 연결: IP주소" 출력
				System.out.println(ChatServer.newClient(clientSocket.getInetAddress()));

				/*
				 * ■ OutputStream on Network (1) Server Side: ● The server uses the input stream
				 * (getInputStream()) of the client's socket to read data sent by the client. ●
				 * The server uses the output stream (getOutputStream()) of the client's socket
				 * to send data to the client. (2) Client Side: ● Conversely, the client uses
				 * the input stream of the server's socket to read data sent by the server. ●
				 * The client uses the output stream of its own socket to send data to the
				 * server.
				 */

				// 서버 → 클라이언트 전송: 서버에서 클라이언트 소켓의 출력 스트림 사용
				PrintWriter writer = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)), true);

				// 이전 대화목록 다시 보여주기 - chatMap에 있던 대화내용 보여주기
				CSVReader csvReader = new CSVReader();
				writer.println(csvReader.readCSV());
				
				// 클라이언트 접속 시 "OOOO님 반갑습니다!" 출력
				writer.println(ChatServer.greeting(clientSocket));
				

				// 클라이언트 → 서버 전송: 서버에서 클라이언트 소켓의 입력 스트림 사용
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

				// 클라이언트로부터 받은 메시지를 서버에서 출력하고, 클라이언트에게도 송출
				String content;
				while ((content = reader.readLine()) != null) {
					// clientsChat - [발신자] (시간) 내용 형식
					String chat = ChatServer.clientsChat(clientSocket.getInetAddress(), content);
					System.out.println(chat); // [발신자] (시간) 내용 형식 출력
					writer.println(chat); // 서버 -> 클라이언트에게 chat 전송
					// Chat.csv 파일에 시간, 유저닉네임, 대화내용 넣기
					ChatServer.addChatCsv(serverSocket.getInetAddress());
					if (content.equals("/종료")) {
						try {
							clientSocket.close();
							serverSocket.close();
							System.out.println("(/종료)대화가 종료되었습니다.");
						} catch (IOException e) {
							System.out.println("에러로 인해 종료되었습니다.");
							System.out.println(e);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
