package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.io.*;

class  ChatClientObject extends JFrame implements ActionListener,Runnable
{
	private JTextArea output; 
	private JTextField input; 
	private JButton sendBtn;
	private Socket socket;
	private ObjectInputStream reader=null;
	private ObjectOutputStream writer=null; 
	//private String msg;
	//private InfoDTO dto;
	private String nickName;
	public ChatClientObject() {
		//센터에 TextArea만들기
		output = new JTextArea();
		output.setFont(new Font("맑은 고딕",Font.BOLD,15));
		output.setEditable(false);
		JScrollPane scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  //항상 스크롤바가 세로로 떠있음
		
		//하단에 버튼과 TextArea넣기 
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout()); 
		input = new JTextField();
		
		sendBtn = new JButton("보내기");
		
		bottom.add("Center",input);  //센터에 붙이기
		bottom.add("East",sendBtn);  //동쪽에 붙이기
		//container에 붙이기
		Container c = this.getContentPane();
		c.add("Center", scroll);  //센터에 붙이기
		c.add("South", bottom);  //남쪽에 붙이기
		//윈도우 창 설정
		setBounds(300,300,300,300);
		setVisible(true);

		//윈도우 이벤트
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){ 
				//System.exit(0);
				try{
					//InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setNickName(nickName);
					dto.setCommand(Info.EXIT);
					writer.writeObject(dto);  //역슬러쉬가 필요가 없음
					writer.flush();
				}catch(IOException io){
					io.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] args) 
	{
		new ChatClientObject().service();
	}
	private void service() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}		
		

}


