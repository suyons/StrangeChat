package multichat;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChatServer extends JFrame implements ActionListener{

	private JPanel panelCenter;
	private JPanel panelSouth;
	
	private JTextField tf;
	private JButton btn;
	private JButton btn2;
	
	private JTextArea ta;
	
	private ServerSocket server = null; 
	private Socket socket = null;		//여러개의 소켓을 받을거기 때문에 제거
//	private BufferedReader in = null;	//인아웃필요없음
//	private BufferedWriter out = null;
	
	private ArrayList<ServerThread> threadList = new ArrayList<>();
	
	
	public MultiChatServer(String title, int width, int height) {
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		setSize(width, height);
		setLayout(new BorderLayout());
		
		setCenter();
		setSouth();
		
		setVisible(true);
		
		tf.requestFocus();
		
	}
	public void setSocketServer() {
		try {
			Time time = new Time();
			
			server = new ServerSocket(9999);
			ta.append(">>연결 대기중...\n");
			
			//여러개가 엑셉트 되어야함
			while(true) {
				Socket socket = server.accept();		//소켓을 엑셉트 시킴
				ta.append("연결 되었습니다!!!\n");	//연결될때마다 리스트를 만들고 add로 스레드리스트 추가
				ServerThread st = new ServerThread(socket, threadList);
				//서버 스레드 생성 후 클래스로 생성
				//소켓을 리셋시킬거임
				//스레드리스트를 만들거임
				threadList.add(st);				//접속된 정보를 추가함
				st.start();					//스레드이기때문에 스타트가 걸려야함
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setBackground(Color.BLUE);
		panelCenter.setLayout(new BorderLayout());
		
		ta = new JTextArea(7,18);
		ta.setLineWrap(true);
		ta.setEditable(false);
		JScrollPane sp = new JScrollPane(ta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelCenter.add(sp);
		add(panelCenter, BorderLayout.CENTER);
		
	}
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
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn || obj == tf) {
			
			localType();
			
		} else if(obj == btn2) {
		}
	}
	
	private void localType() {
//		try {
//			Time time = new Time();
//			
//			String outMessage = tf.getText();	//textfield에 있는 메세지를 외부(클)로 보낸다
//			out.write(outMessage + "\n");	//try캐치문 실행
//			out.flush();
//			
//			ta.append("[서버]  "+time.timeInfo()+ "\n" + outMessage + "\n");
//			tf.setText("");
//			tf.requestFocus();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		
	}
	
	public JButton getBtn() {
		return btn;
		
	}
	public JTextArea getTa() {
		return ta;
	}
	
	public static void main(String[] args) {
		MultiChatServer cs = new MultiChatServer("채팅서버", 300, 400);
		cs.setSocketServer();
		
	}
	
	
	
}