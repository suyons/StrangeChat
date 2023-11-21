package csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import common.Chat;
import common.Constants;
import common.User;

// 지은 파일 입출력 추가
public class ChatServer {
	// userMap: K=IP주소, V=닉네임 + ChatMap: K=타임스탬프, V=대화내용
	private static HashMap<InetAddress, String> userMap = new LinkedHashMap<InetAddress, String>();
	private static HashMap<Long, String> chatMap = new LinkedHashMap<Long, String>();

	// userMap에서 K=IP주소 넣고 V=닉네임 꺼내기
	static String getUserName(InetAddress ipAddr) {
		return userMap.get(ipAddr);
	}

	// chatMap에서 K=타임스탬프 넣고 V=대화내용 꺼내기
	static String getChatContents(Long timestamp) {
		return chatMap.get(timestamp);
	}

	// userMap에서 <K, V> 1쌍 추가
	static void addUser(User user) {
		userMap.put(user.ipAddr, user.userName);
	}

	// chatMap에서 <K, V> 1쌍 추가
	static void addChat(Chat chat) {
		chatMap.put(chat.timestamp, chat.content);
	}

	// User.csv 파일에 IP주소, 닉네임 기록
	static void addUserCsv(InetAddress ipAddr) {

		try (FileWriter fw = new FileWriter("User.csv", true)) {
//				fw.write("IP주소, 닉네임");  이거 이어쓰기모드(true)에서는 적용하면 계속 추가됨
			Iterator<InetAddress> ir = userMap.keySet().iterator();
			while (ir.hasNext()) {
				ipAddr = ir.next();
				String userName = userMap.get(ipAddr);

				String IpAdress = ipAddr.getHostAddress();

				fw.write("\n");
				fw.write(IpAdress);
				fw.write(",");
				fw.write(userName);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Chat.csv 파일에 시간, 대화내용, 닉네임 입력
	public static void addChatCsv(InetAddress ipAddr) {

		try (FileWriter fw = new FileWriter("Chat.csv", true)) {
//					fw.write("시간, 닉네임, 대화내용"); 이거 이어쓰기모드(true)에서는 적용하면 계속 추가됨
			// 대화 출력을 위한 반복자
			Iterator<Long> chatIr = chatMap.keySet().iterator();
			while (chatIr.hasNext()) {
				Long timestamp = chatIr.next();
				String time = Chat.dateTime(timestamp);
				String chat = chatMap.get(timestamp);

				// 유저 이름 출력을 위한 반복자
				Iterator<InetAddress> userIr = userMap.keySet().iterator();
				while (userIr.hasNext()) {
					ipAddr = userIr.next();
					String userName = userMap.get(ipAddr);

					fw.write("\n");
					fw.write(time);
					fw.write(",");
					fw.write(userName); 
					fw.write(",");
					fw.write(chat);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//chatMap에 있는 이전 대화 목록 꺼내오기
//	public void beforeChat() {
//		try(FileReader fr = new FileReader("Chat.csv")){
//			int i;
//			fr.r
//			while() {
//				System.out.println((char)i);
//			}
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//	}

	// ServerSocket 생성 이후 클라이언트 대기 메시지 출력
	// [시스템] 글머리: 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
	// Chat 클래스에서 String content를 반환하도록 toString() 재정의됨
	static Chat waiting() { // 원래 Chat타입
		Chat chat = new Chat();
		chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp) + Constants.SERVER_PORT
				+ "번 포트 연결 대기 중입니다.";
//        addChat(chat);  이거 추가하면 대화내용 csv 파일에 나옴
		return chat;
	}

	// 클라이언트 접속 시 IP주소를 출력
	// [시스템] 글머리: 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
	static Chat newClient(InetAddress ipAddr) {
		Chat chat = new Chat();
		chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp) + "새 클라이언트 연결: " + ipAddr + " <"
				+ getUserName(ipAddr) + ">";
//        addChat(chat);  이거 추가하면 대화내용 csv 파일에 나옴
		return chat;
	}

	// 클라이언트 접속 시 userMap에서 해당 사용자명을 찾아오며 환영인사를 반환
	// [서버] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
	static Chat greeting(Socket clientSocket) {
		Chat chat = new Chat();
		chat.content = Constants.SERVER_NAME + Chat.hourMinute(chat.timestamp)
				+ getUserName(clientSocket.getInetAddress()) + "님 반갑습니다!";
//        addChat(chat);	이거 추가하면 대화내용 csv 파일에 나옴
		return chat;
	}

	// 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
	// [사용자명] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
	static String clientsChat(InetAddress ipAddr, String content) {
		Chat chat = new Chat();
		chat.content = content; //대화 내용만 출력하고 싶어서 [발신자] (시간) 내용 형식 말고 그냥 content 대입 
		addChat(chat); // chatMap에 저장하는 메서드
		return "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
	}

	
	
	
	// (미구현) 기능 추가 예시: 사용자명 변경
	static void changeUserName(String name) {
		// HashMap의 Value 수정
		// CSV 파일에 저장된 내용 수정
	}

}
