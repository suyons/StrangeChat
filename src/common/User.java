package common;

import java.util.Arrays;
import java.util.List;

import server.ChatServer;

public class User {
    /* 이름 구성: 색상 2자 + 농산물 2자, List에서 무작위 추출하여 결합할 것 */
    static List<String> nameList1 = Arrays.asList("빨강", "주황", "노랑", "초록", 
    "연두", "파랑", "보라", "분홍", "황금", "검정", "하늘", "하양");
    static List<String> nameList2 = Arrays.asList("사과", "자두", "오이", "피망", 
    "배추", "상추", "가지", "감자", "당근", "참깨", "보리", "감귤");
    // userMap의 Key: IP주소
    public final String ipAddr;
    // userMap의 Value: 사용자 이름
    public String userName;

    /*
     * 생성자 재정의: 위의 2개 배열에서 무작위 추출하여
     * 2글자 색상 + 2글자 농산물로 결합하여 지정하기
     * HashMap에 이미 있다면 이미 있는 것으로 지정한다.
     * 서버의 IP주소와 일치하면 이름을 [관리자]로 설정
     */
    public User(String ipAddr) {
        this.ipAddr = ipAddr;
        if (this.ipAddr.equals(Constants.SERVER_ADDR))
            this.userName = Constants.ADMIN_NAME;
        else if (ChatServer.getUserName(ipAddr) != null)
            this.userName = ChatServer.getUserName(ipAddr);
        else
            this.userName = nameList1.get((int) (Math.random() * 12))
             + nameList2.get((int) (Math.random() * 12));
    }
}
