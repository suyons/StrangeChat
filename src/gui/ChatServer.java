package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatServer extends JFrame implements ActionListener{

	private JPanel panelCenter;
	private JPanel panelSouth;
	private JPanel panelNorth;
	
	private JTextField tf;
	private JButton btn;
	private JButton btn2;
	
	private JTextArea ta;
	
	private ServerSocket server = null;
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	
	//
	public ChatServer(String title, int width, int height) {
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		setSize(width, height);
		setLayout(new BorderLayout());
		
		setCenter();
		setSouth();
		setNorth();
		
		setVisible(true);
		
		tf.requestFocus();
		
		
	}
	

	//소켓서버
	public void setSocketServer() {
		try {
			server = new ServerSocket(9999);
			ta.append(">>연결 대기중...\n");
			
			socket = server.accept();
			ta.append("연결 되었습니다!!!\n");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {
				String inMsg = in.readLine();
				if(inMsg.equalsIgnoreCase("bye")) {
					System.out.println("클라이언트가 나갔습니다.");
					break;
				}
				//정상메시지인경우
				ta.append("[클라이언트]"+"\n" + inMsg + "\n");
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				socket.close();
				server.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//위 - 유저 목록창 
	private void setNorth() {
		panelNorth = new JPanel();
		panelNorth.setBackground(Color.lightGray);
		panelNorth.setLayout(new BorderLayout());
		
		//라벨 - 접속중인 유저
		JLabel userList = new JLabel("접속중인 유저");
		panelNorth.add(userList, BorderLayout.NORTH);
		
		JTextArea taUser = new JTextArea(5,5);
		panelNorth.add(taUser, BorderLayout.SOUTH);
		taUser.setEditable(false);
		
		add(panelNorth, BorderLayout.NORTH);
	}
	
	//중앙 - 대화 출력창
	private void setCenter() {
		panelCenter = new JPanel();
//		panelCenter.setBackground(Color.BLUE);
		panelCenter.setLayout(new BorderLayout());
		
		ta = new JTextArea(7,18);
		ta.setLineWrap(true);	//줄바꿈 메서드
		ta.setEditable(false); 	//수정여부 메서드
		JScrollPane sp = new JScrollPane(ta,	//스크롤 메서드
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelCenter.add(sp);	//스크롤 패널에 붙임
		add(panelCenter, BorderLayout.CENTER);
		
	}
	
	//아래 - 메시지 입력창 + 전송버튼
	private void setSouth() {
		panelSouth = new JPanel();
		tf = new JTextField(18);
		tf.addActionListener(this);
		panelSouth.add(tf);
		
		btn = new JButton("전송");
		btn.addActionListener(this);
		
		btn2 = new JButton("전송");
		btn2.addActionListener(this);
		
		panelSouth.add(btn);
		
		add(panelSouth, BorderLayout.SOUTH);
		
	}
	
	//전송버튼 액션
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn || obj == tf) {
			
			localType();
			
		} else if(obj == btn2) {
		}
	}
	
	
	//
	private void localType() {
		try {
			String outMessage = tf.getText();	//textfield에 있는 메세지를 외부(클)로 보낸다
			out.write(outMessage + "\n");	//try캐치문 실행
			out.flush();
			ta.append("[서버]"+"\n" + outMessage +  "\n");
			tf.setText("");
			tf.requestFocus();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public JButton getBtn() {
		return btn;
		
	}
	public JTextArea getTa() {
		return ta;
	}
	
	public static void main(String[] args) {
		ChatServer cs = new ChatServer("채팅서버", 300, 400);
		cs.setSocketServer();
	}
	
	
	
}