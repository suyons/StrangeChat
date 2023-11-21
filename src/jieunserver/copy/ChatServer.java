package jieunserver.copy;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import common.Chat;
import common.Constants;
import common.User;


// 지은 파일 입출력 추가
public class ChatServer {
    // userMap: K=IP주소, V=닉네임 + ChatMap: K=타임스탬프, V=대화내용
    private static HashMap<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private static HashMap<Long, String> chatMap = new HashMap<Long, String>();

    
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
		Iterator<InetAddress> ir = userMap.keySet().iterator();
		while (ir.hasNext()) {
			ipAddr = ir.next();
			String userName = userMap.get(ipAddr);

			try (FileWriter fw = new FileWriter("User.csv")) { //true 파일을 초기화하지 않고 그대로 이어쓰는 방식
				String IpAdress = ipAddr.getHostAddress();
				
				fw.write("\n");
				fw.write(IpAdress);
				fw.write(", ");
				fw.write(userName);
				

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	// Chat.csv 파일에 시간, 대화내용, 닉네임 입력
		public static void addChatCsv(InetAddress ipAddr) throws IOException {
			Iterator<Long> ir = chatMap.keySet().iterator();
			
			
			while (ir.hasNext()) {
				Long timestamp = ir.next();
				String time = Chat.hourMinute(timestamp);
				String userName = userMap.get(ipAddr);
				String chat = chatMap.get(timestamp);
				
				try (FileWriter fw = new FileWriter("Chat.csv")) {
					fw.write("시간, 닉네임, 유저대화");
					fw.write("\n");
					fw.write(time);
//					fw.write(", ");
//					fw.write(userName); 	 //여기서 IOException
					fw.write(", ");
					fw.write(chat);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
    
    

    // ServerSocket 생성 이후 클라이언트 대기 메시지 출력
    // [시스템] 글머리: 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
    // Chat 클래스에서 String content를 반환하도록 toString() 재정의됨
    static Chat waiting() {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + Constants.SERVER_PORT + "번 포트 연결 대기 중입니다.";
        addChat(chat);
        return chat;
    }

    // 클라이언트 접속 시 IP주소를 출력
    // [시스템] 글머리: 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
    static Chat newClient(InetAddress ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + "새 클라이언트 연결: " + ipAddr + " <" + getUserName(ipAddr) + ">";
        addChat(chat);
        return chat;
    }

    // 클라이언트 접속 시 userMap에서 해당 사용자명을 찾아오며 환영인사를 반환
    // [서버] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
    static Chat greeting(Socket clientSocket) {
        Chat chat = new Chat();
        chat.content = Constants.SERVER_NAME + Chat.hourMinute(chat.timestamp)
                + getUserName(clientSocket.getInetAddress()) + "님 반갑습니다!";
        addChat(chat);
        return chat;
    }

    // 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
    // [사용자명] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientsChat(InetAddress ipAddr, String content) {
        Chat chat = new Chat();
        chat.content = "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
        chat.onlyContent = content;
        addChat(chat);
        return chat;
    }

    // (미구현) 기능 추가 예시: 사용자명 변경
    static void changeUserName(String name) {
        // HashMap의 Value 수정
        // CSV 파일에 저장된 내용 수정
    }
    
}
