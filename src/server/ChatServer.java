package server;

import java.net.InetAddress;
import java.util.HashMap;

import common.*;

public class ChatServer {
    private static HashMap<InetAddress, String> userMap = new HashMap<InetAddress, String>();
    private static HashMap<Long, String> chatMap = new HashMap<Long, String>();

    public static String getUserName(InetAddress ipAddr) {
        return userMap.get(ipAddr);
    }

    public static String getChatContents(Long timestamp) {
        return chatMap.get(timestamp);
    }

    public static void addUser(User user) {
        userMap.put(user.ipAddr, user.userName);
    }

    public static void addChat(Chat chat) {
        chatMap.put(chat.timestamp, chat.content);
    }
}
