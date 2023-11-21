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
			serverSocket = new ServerSocket(Constants.SERVER_PORT);	//서버소캣 생성
			System.out.println(ChatServer.waiting());	//클라이언트 대기 메시지 출력
			
			while (true) {
				clientSocket = serverSocket.accept(); // serverSocket.accept(): 연결된 Socket 객체를 반환
				
				// 이전 대화목록 다시 보여주기 - chatMap에 있던 대화내용 보여주기
				CSVReader csvReader = new CSVReader(); //csv파일 hashmap에 저장하는 클래스
				csvReader.saveChatCsv();	//csv파일 hashmap에 저장하는 메서드
				csvReader.saveUserCsv();	//csv파일 usermap에 저장하는 메서드
				ChatServer.printChatmap(); //이전 대화내용 출력
				
				// V HashMap.get(K): HashMap에서 K에 해당하는 V 페어가 없다면 null을 반환
				// getUserName() : K값 ip주소 넣어서 V값 가져오기
				// 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
				if (ChatServer.getUserName(clientSocket.getInetAddress()) == null) {
					//여기에 csv파일에 저장된 값 userMap에 올려야함 (그래야 비교 가능)
					
					//첫 방문자 새로운 닉네임 주고 기록을 user맵에 저장 
					ChatServer.addUser(new User(clientSocket.getInetAddress()));
					// User.csv 파일에 생성된 유저 아이피주소, 닉네임 저장하기 
					ChatServer.addUserCsv(clientSocket.getInetAddress());
				} else {
					
				}
				
				// "새 클라이언트 연결: IP주소" 출력
				System.out.println(ChatServer.newClient(clientSocket.getInetAddress()));


				// 서버 → 클라이언트 전송: 서버에서 클라이언트 소켓의 출력 스트림 사용
				PrintWriter writer = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)), true);

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
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
