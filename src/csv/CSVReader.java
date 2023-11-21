package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

	// Chat.csv 파일 해쉬맵에 저장하기 -> 파일 불러와서 시간, 대화내용을 해쉬맵에 저장한다.
	public void readCSV() {

		String line;
//		String timestamp;
//		String content = null;
//		Long longTimestamp = null;

		// Chat.csv - (배열 분류)
		try (BufferedReader fr = new BufferedReader(new FileReader("Chat.csv"))) {

			while ((line = fr.readLine()) != null) {// csv 파일의 한 줄을 불러온다.
				String[] data = line.split(","); // 한줄에서 쉼표를 기준으로 문자열을 분리한다.
				if (data[0].equals("시간")) // 만약 data[0]에 들어가는 문자가 시간이면 while문을 스킵한다. -> 첫번째 행 스킵
					continue;
				ChatServer.putCsv(Long.valueOf(data[0]), data[1]);

				/*
				 * for (int i = 2; i < data.length; i++) { if (i % 2 == 0) { timestamp =
				 * data[i]; longTimestamp = Long.valueOf(timestamp); //timestamp가 String형이므로
				 * Long형으로 바꿔야한다. } else if (i % 3 == 1) { name = data[i]; } else if (i % 2 !=
				 * 0) { content = data[i]; } else break; }
				 * 
				 * ChatServer.putCsv(longTimestamp, content);
				 */
			}
			fr.close();
			// putCsv: CSV → HashMap으로 쓰기
			// chatMap에 csv 파일 넣기
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

//
//	static void testLine() {
//		String line;
//		try (BufferedReader fr = new BufferedReader(new FileReader("Chat.csv"))) {
//
//			while ((line = fr.readLine()) != null) {
//				String[] data = line.split(",");
//				if (data[0].equals("시간"))
//					continue;
//				System.out.println(data[0] + "," + data[1] + "," + data[2]);
//			}
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//	}
//
//	public static void main(String[] args) {
//		testLine();
//	}
//}

//	// 클라이언트가 전송한 채팅을 chatMap에 저장하고, [발신자] (시간) 내용 형식으로 반환
//	// [사용자명] 글머리: 모든 클라이언트와 서버에서 표시됩니다.
//	static String clientsChat(InetAddress ipAddr, String content) {
//		Chat chat = new Chat();
//		chat.content = content; //대화 내용만 출력하고 싶어서 [발신자] (시간) 내용 형식 말고 그냥 content 대입 
//		addChat(chat); // chatMap에 저장하는 메서드
//		return "[" + getUserName(ipAddr) + "] " + Chat.hourMinute(chat.timestamp) + content;
//	}
