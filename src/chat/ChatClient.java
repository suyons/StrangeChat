package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener{

	private JPanel panelCenter;
	private JPanel panelSouth;
	
	private JTextField tf;
	private JButton btn;
	private JButton btn2;
	
	private JTextArea ta;
	
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	public ChatClient(String title, int width, int height) {
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(800, 200);
		setSize(width, height);
		setLayout(new BorderLayout());
		
		setCenter();
		setSouth();
		setVisible(true);
		tf.requestFocus();
		
		
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
		try {
			String outMessage = tf.getText();	//textfield에 있는 메세지를 외부(클)로 보낸다
			out.write(outMessage + "\n");	//try캐치문 실행
			out.flush();
			
			ta.append("[클라이언트] : " + outMessage + "\n");
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
	
	public void setSocket() {
		try {
			socket = new Socket("localhost", 9999);
			ta.append(">>연결 완료!!!\n");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {	//와일문 돌면서 뭔가를 받으면 찍겠다
				String inMsg = in.readLine();
				ta.append("[서버] : " + inMsg+ "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ChatClient cc = new ChatClient("채팅클라이언트", 300, 400);
		cc.setSocket();
	}
	
	
	
}