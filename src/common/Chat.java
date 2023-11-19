package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {
    private Date now = new Date();
    public final long timestamp = now.getTime();

    public String content;

    public static String timeFormat(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date(stamp));
        return dateTime;
    }
}
