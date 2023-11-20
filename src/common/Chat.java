package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {
    private Date now = new Date();
    public final long timestamp;

    public String content = "";

    public Chat() {
        timestamp = now.getTime();
    }

    // 연-월-일 시:분:초
    public static String dateTime(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date(stamp));
        return dateTime;
    }

    // 시:분
    public static String hourMinute(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("(HH:mm) ");
        String hourMins = sdf.format(new Date(stamp));
        return hourMins;
    }

    @Override
    public String toString() {
       return this.content;
    }
}
