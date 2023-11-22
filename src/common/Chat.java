package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {
    // Date() 생성자: 인스턴스가 생성된 시각으로 초기화
    private Date now = new Date();
    // chatMap의 Key: UNIX timestamp
    public final long timestamp;
    // chatMap의 Value: 대화내용
    public String content = "";

    // 생성자 → 현재 시각의 UNIX Timestamp를 입력
    /* UNIX 시간: 1970-01-01 00:00 (UTC)부터 지금까지 몇 초 지났나요? */
    public Chat() {
        timestamp = now.getTime();
    }

    // (시:분) 형식으로 반환
    public static String hourMinute(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("(HH:mm) ");
        String hourMins = sdf.format(new Date(stamp));
        return hourMins;
    }

    // toString 메서드를 재정의
    // print(Chat.content) 대신 print(Chat)으로 대화내용 출력
    @Override
    public String toString() {
       return this.content;
    }
}
