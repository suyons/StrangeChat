package server;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import common.*;

public class ChatServer {
    private static HashMap<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private static HashMap<Long, String> chatMap = new HashMap<Long, String>();

    static String getUserName(InetAddress ipAddr) {
        return userMap.get(ipAddr);
    }

    static String getChatContents(Long timestamp) {
        return chatMap.get(timestamp);
    }

    static void addUser(User user) {
        userMap.put(user.ipAddr, user.userName);
    }

    static void addChat(Chat chat) {
        chatMap.put(chat.timestamp, chat.content);
    }

    static Chat waiting() {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
        + Constants.SERVER_PORT + "번 포트 연결 대기 중입니다.";
        addChat(chat);
        return chat;
    }

    static Chat newClient(InetAddress addr) {
        Chat chat = new Chat();
        chat.content = Constants.SYSTEM_NAME + Chat.hourMinute(chat.timestamp)
        + "새 클라이언트 연결: " + addr;
        addChat(chat);
        return chat;
    }

    static Chat greeting(Socket clientSocket) {
        Chat chat = new Chat();
        chat.content = Constants.SERVER_NAME + Chat.hourMinute(chat.timestamp)
        + getUserName(clientSocket.getInetAddress()) + "님 반갑습니다!";
        addChat(chat);
        return chat;
    }

    static void changeUserName(String name) {
        // HashMap의 Value 수정
        // CSV 파일에 저장된 내용 수정
    }
}
