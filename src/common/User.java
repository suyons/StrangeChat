package common;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class User {
    static List<String> nameList1 = Arrays.asList("빨강", "주황", "노랑", "초록", "연두", "파랑", "보라", "분홍");
    static List<String> nameList2 = Arrays.asList("사과", "자두", "오이", "피망", "배추", "상추", "가지", "감자");
    public InetAddress ipAddr;
    public String userName;

    public User(InetAddress ipAddr) {
        this.ipAddr = ipAddr;
        this.userName = nameList1.get((int) (Math.random() * 8)) + nameList2.get((int) (Math.random() * 8));
    }
}
