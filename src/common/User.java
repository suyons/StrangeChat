package common;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class User {
    static List<String> nameList1 = Arrays.asList("빨강", "주황", "노랑", "초록", "연두"
    , "파랑", "보라", "분홍", "황금", "검정", "하늘", "하양");
    static List<String> nameList2 = Arrays.asList("사과", "자두", "오이", "피망", "배추"
    , "상추", "가지", "감자", "당근", "참깨", "보리", "감귤");
    public final InetAddress ipAddr;
    public String userName;

    // 생성자 재정의: 위의 2개 배열에서 무작위 추출 → 2글자 + 2글자로 결합하여 지정하기
    public User(InetAddress ipAddr) {
        this.ipAddr = ipAddr;
        this.userName = nameList1.get((int) (Math.random() * 12)) + nameList2.get((int) (Math.random() * 12));
    }
}
