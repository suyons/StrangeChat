package newtest.server2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

public class CSVReader {

	// Chat.csv 파일 해쉬맵에 저장하기 -> 파일 불러와서 시간, 대화내용을 해쉬맵에 저장한다.
	public void saveChatCsv() {
		String line;
		
		// Chat.csv - (배열 분류)
		try (BufferedReader fr = new BufferedReader(new FileReader("Chat.csv"))) {
			while ((line = fr.readLine()) != null) {// csv 파일의 한 줄을 불러온다.
				String[] data = line.split(","); // 한줄에서 쉼표를 기준으로 문자열을 분리한다.
				if (data[0].equals("시간")) // 만약 data[0]에 들어가는 문자가 시간이면 while문을 스킵한다. -> 첫번째 행 스킵
					continue;
				
				// putChatmap() : 타임스탬프, 대화내용 매개변수로 받아 csv 파일 -> hashmap에 저장
				ChatServer.addChat(Long.valueOf(data[0]), data[1]); // 한줄을 기준으로 데이터가 두개이기 때문
				
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	

	
	// User.csv 파일 해쉬맵에 저장하기 -> 파일 불러와서 아이피주소, 닉네임을 해쉬맵에 저장한다.
	public void saveUserCsv() {
		String line;
		// User.csv - (배열 분류)
		try (BufferedReader fr = new BufferedReader(new FileReader("User.csv"))) {
			while ((line = fr.readLine()) != null) {// csv 파일의 한 줄을 불러온다.
				String[] data = line.split(","); // 한줄에서 쉼표를 기준으로 문자열을 분리한다.
				if (data[0].equals("IP주소")) // 만약 data[0]에 들어가는 문자가 IP주소이면 while문을 스킵한다. -> 첫번째 행 스킵
					continue;
				// putChatmap() : 타임스탬프, 대화내용 매개변수로 받아 csv 파일 -> hashmap에 저장
				ChatServer.addUser(data[0], data[1]); // 한줄을 기준으로 데이터가 두개이기 때문
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
