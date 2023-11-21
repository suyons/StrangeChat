package server;

import java.util.ArrayList;
import java.util.HashMap;

import common.*;

public class ChatServer {
    // 멀티스레드: 클라이언트 스레드를 ArrayList로 관리
    private static ArrayList<ServerThread> threadList = new ArrayList<>();

    static ArrayList<ServerThread> getThreadList() {
        return threadList;
    }

    // userMap: K=IP주소, V=닉네임 + ChatMap: K=타임스탬프, V=대화내용
    private static HashMap<String, String> userMap = new HashMap<String, String>();
    private static HashMap<Long, String> chatMap = new HashMap<Long, String>();

    // userMap에서 K=IP주소 넣고 V=닉네임 꺼내기
    static String getUserName(String ipAddr) {
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

    // addUser() 메서드 오버로딩 → Load CSV to HashMap
    static void addUser(String ipAddr, String userName) {
        userMap.put(ipAddr, userName);
    }

    // chatMap에서 <K, V> 1쌍 추가
    static void addChat(Chat chat) {
        chatMap.put(chat.timestamp, chat.content);
    }

    // addChat() 메서드 오버로딩 → Load CSV to HashMap
    static void addChat(long timestamp, String content) {
        chatMap.put(timestamp, content);
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
    static Chat newClient(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + "새 클라이언트 연결: " + ipAddr + " <" + getUserName(ipAddr) + ">";
        addChat(chat);
        return chat;
    }

    // 클라이언트 접속 시 userMap에서 해당 사용자명을 찾아오며 환영인사를 반환
    // [서버] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientJoined(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SERVER_NAME + Chat.hourMinute(chat.timestamp)
                + getUserName(ipAddr) + "님께서 입장하셨습니다.";
        addChat(chat);
        return chat;
    }

    // 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
    // [사용자명] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientsChat(String ipAddr, String content) {
        Chat chat = new Chat();
        chat.content = "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
        addChat(chat);
        return chat;
    }

    static Chat clientLeft(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + getUserName(ipAddr) + "님께서 퇴장하셨습니다.";
        addChat(chat);
        return chat;
    }

    // (미구현) 기능 추가 예시: 사용자명 변경
    static void changeUserName(String ipAddr, String name) {
        // HashMap의 Value 수정
        // CSV 파일에 저장된 내용 수정
    }
}
