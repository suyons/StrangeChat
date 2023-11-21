package donotuse;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Time {
	public String timeInfo() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat time = new SimpleDateFormat ("aa hh:mm");
		return (String)time.format(timestamp);
		}
	}