package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	
	// Chat.csv 파일 읽어오기 -> [발신자] (시간) 내용 형식으로 다시 대화출력창에 나와야함. 
	
	public void readCSV() {
		
		String line;
		String time = null;
		String name = null;
		String content = null;
		//Chat.csv - (배열 분류)
		try(BufferedReader fr = new BufferedReader(new FileReader("Chat.cvs"))){
			
			while((line = fr.readLine()) != null ) {
				String[] data = line.split(",");
				
				for(int i = 3; i < data.length; i++) {
					if(i%3 == 0) {
						time = data[i];
					} else if(i%3 == 1) {
						name = data[i];
					} else if(i%3 == 2) {
						content = data[i];
					} else break;
				} System.out.println("[" + name + "] " + time + content);
			} fr.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//	// 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
//	// [사용자명] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
//	static String clientsChat(InetAddress ipAddr, String content) {
//		Chat chat = new Chat();
//		chat.content = content; //대화 내용만 출력하고 싶어서 [발신자] (시간) 내용 형식 말고 그냥 content 대입 
//		addChat(chat); // chatMap에 저장하는 메서드
//		return "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
//	}
	
}
