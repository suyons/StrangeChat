package example;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class actionPerformed {
	public void actionPerformed(ActionEvent e) throws IOException{
			//서버로 보냄 
			//JTextField값을 서버로보내기
			//버퍼 비우기
			String msg=input.getText();
			InfoDTO dto = new InfoDTO();
			//dto.setNickName(nickName);
			if(msg.equals("exit")){
				dto.setCommand(Info.EXIT);
			} else {
				dto.setCommand(Info.SEND);
				dto.setMessage(msg);
				String nickName = null;
				dto.setNickName(nickName);
			}
			writer.writeObject(dto);
			writer.flush();
			input.setText("");
	}

}
