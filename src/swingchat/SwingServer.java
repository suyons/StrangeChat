package swingchat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Constants;
import common.User;

public class SwingServer extends JFrame implements ActionListener {

	private JPanel panelCenter;
	private JPanel panelSouth;
	
	private JTextField tf;
	private JButton btn;
	private JButton btn2;
	
	private JTextArea textArea;
	
	// private Socket socket = null;		//여러개의 소켓을 받을거기 때문에 제거

	/* Buffered 보조 스트림은 ServerThread의 run()에서 인스턴스화하므로
	 * 여기에서 생성할 필요가 없다.
	 */
	// private BufferedReader in = null;
	// private BufferedWriter out = null;
	
	private ArrayList<ServerThread> threadList = new ArrayList<>();
	
	public SwingServer(String title, int width, int height) {
		
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
		try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println(ChatServer.waiting());
			
			while(true) {
				// serverSocket.accept(): 연결된 Socket 객체를 반환
				Socket clientSocket = serverSocket.accept();

				if (ChatServer.getUserName(clientSocket.getInetAddress()) == null)
					// 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
                    ChatServer.addUser(new User(clientSocket.getInetAddress()));

				textArea.append("연결 되었습니다!!!\n");	//연결될때마다 리스트를 만들고 add로 스레드리스트 추가
				ServerThread st = new ServerThread(clientSocket, threadList);
				//서버 스레드 생성 후 클래스로 생성
				//소켓을 리셋시킬거임
				//스레드리스트를 만들거임
				threadList.add(st);				//접속된 정보를 추가함
				// Causes this thread to begin execution; the Java Virtual Machine calls the run method of this thread.
				st.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setBackground(Color.BLUE);
		panelCenter.setLayout(new BorderLayout());
		
		textArea = new JTextArea(7,18);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane sp = new JScrollPane(textArea,
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
//			textArea.append("[서버]  "+time.timeInfo()+ "\n" + outMessage + "\n");
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
		return textArea;
	}
	
	public static void main(String[] args) {
		SwingServer cs = new SwingServer("채팅서버", 300, 400);
		cs.setSocketServer();
		
	}
	
	
	
}