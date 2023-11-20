package ChatTime;

public class chatTime {
	
	public static void main(String[] args) {
	
		
		long chatTime = System.currentTimeMillis();
		
		
		int minutes = (int) ((chatTime / 1000) / 60);
		
		
		
		if (minutes < 10) {
		    System.out.println("채팅이 도착한 시간은 " + minutes + "분 전입니다.");
		} else {
		    System.out.println("채팅이 도착한 시간은 " + minutes + "분 전입니다.");
		}
	}
	
}
