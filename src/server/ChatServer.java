package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import common.Chat;
import common.Constants;
import common.User;

public class ChatServer {
    // 멀티스레드: 클라이언트 스레드를 ArrayList로 관리
    private static ArrayList<ServerThread> threadList = new ArrayList<>();

    // ArrayList get() 메서드
    static ArrayList<ServerThread> getThreadList() {
        return threadList;
    }

    // userMap: K(IP주소) + V(닉네임) / ChatMap: K(타임스탬프) + V(대화내용)
    private static HashMap<String, String> userMap = new LinkedHashMap<String, String>();
    private static HashMap<Long, String> chatMap = new LinkedHashMap<Long, String>();

    // userMap에서 K=IP주소 넣고 V=닉네임 꺼내기
    public static String getUserName(String ipAddr) {
        return userMap.get(ipAddr);
    }

    // chatMap에서 K=타임스탬프 넣고 V=대화내용 꺼내기
    static String getChatContents(Long timestamp) {
        return chatMap.get(timestamp);
    }

    // chatMap 반복자 get() 메서드
    static Iterator<Long> getIterator() {
        return chatMap.keySet().iterator();
    }

    // userMap에서 <K, V> 1쌍 추가
    static void addUser(User user) {
        userMap.put(user.ipAddr, user.userName);
        CSVReader.addUserCsv(user);
    }

    // addUser() 메서드 오버로딩 → CSV 파일 불러오기
    static void addUser(String ipAddr, String userName) {
        userMap.put(ipAddr, userName);
    }

    // chatMap에서 <K, V> 1쌍 추가
    static void addChat(Chat chat) {
        chatMap.put(chat.timestamp, chat.content);
        CSVReader.addChatCsv(chat);
    }

    // addChat() 메서드 오버로딩 → CSV 파일 불러오기
    static void addChat(long timestamp, String content) {
        chatMap.put(timestamp, content);
    }

    /*
     * ServerSocket 생성 이후 클라이언트 대기 메시지 출력
     * [시스템] 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
     * Chat 클래스에서 String content를 반환하도록 toString() 재정의됨
     */
    static Chat waiting() {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + Constants.SERVER_PORT + "번 포트 연결 대기 중입니다.";
        // addChat(chat);
        return chat;
    }

    // 클라이언트 접속 시 IP주소를 출력
    // [시스템] 서버에서만 보입니다. 클라이언트로 전송하지 않습니다.
    static Chat newClient(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + "새 클라이언트 연결: " + ipAddr + " <" + getUserName(ipAddr) + ">";
        // addChat(chat);
        return chat;
    }

    // 클라이언트 접속 시 userMap에서 해당 사용자명을 찾아와 입장 알림
    // [시스템] 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientJoined(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + getUserName(ipAddr) + "님께서 입장하셨습니다.";
        addChat(chat);
        return chat;
    }

    // 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
    // [사용자명] 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientsChat(String ipAddr, String content) {
        Chat chat = new Chat();
        chat.content = "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
        addChat(chat);
        return chat;
    }

    // 클라이언트 앱 종료로 SocketException이 발생했을 때 퇴장 알림
    // [시스템] 모든 클라이언트와 서버에서 표시됩니다.
    static Chat clientLeft(String ipAddr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
                + getUserName(ipAddr) + "님께서 퇴장하셨습니다.";
        addChat(chat);
        return chat;
    }
}
