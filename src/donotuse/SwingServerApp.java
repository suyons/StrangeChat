package donotuse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Constants;
import common.User;

public class SwingServerApp extends JFrame implements ActionListener {

	private JPanel panelCenter;
	private JPanel panelSouth;

	private JTextField textField;
	private JButton button1;
	// private JButton button2;

	private JTextArea textArea;

	SwingServerThread thread;

	public SwingServerApp(String title, int width, int height) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		setSize(width, height);
		setLayout(new BorderLayout());

		setCenter();
		setSouth();

		setVisible(true);

		textField.requestFocus();
	}

	public void setSocketServer() {
		try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
			textArea.append(SwingChatServer.waiting().toString());

			while (true) {
				// serverSocket.accept(): 연결된 Socket 객체를 반환
				Socket clientSocket = serverSocket.accept();

				if (SwingChatServer.getUserName(clientSocket.getInetAddress()) == null)
					// 기존 접속 기록이 없다면 K-V 페어 레코드를 HashMap에 추가
					SwingChatServer.addUser(new User(clientSocket.getInetAddress()));

				// "새 클라이언트 연결: IP주소" 출력
				textArea.append(SwingChatServer.newClient(clientSocket.getInetAddress()).toString());
				SwingServerThread thread = new SwingServerThread(clientSocket);

				/*
				 * Part II: 멀티스레딩, 클라이언트 스레드를 ArrayList에 추가하고
				 * ClientThread 클래스의 run()을 수행
				 */
				SwingChatServer.getThreadList().add(thread);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setBackground(Color.BLUE);
		panelCenter.setLayout(new BorderLayout());

		textArea = new JTextArea(7, 18);
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
		textField = new JTextField(18);
		textField.addActionListener(this);
		panelSouth.add(textField);

		button1 = new JButton("전송");
		button1.addActionListener(this);

		// button2 = new JButton("전송");
		// button2.addActionListener(this);

		panelSouth.add(button1);

		add(panelSouth, BorderLayout.SOUTH);

	}

	/*
	 * ■ ActionListener 인터페이스 구현
	 * ActionEvent is generated when the component-specific action occurs 
	 * (such as being pressed).
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// obj = ActionEvent 발생 위치
		Object obj = e.getSource();
		// 
		if (obj == button1 || obj == textField) {
			adminChat();
		} 
		// else if (obj == button2) {}
	}

	// 관리자 → 클라이언트 메시지 전송
	private void adminChat() {
		try {
			Time time = new Time();

			String outMessage = textField.getText();
			thread.writer.write(outMessage + "\n");
			thread.writer.flush();

			textArea.append(Constants.ADMIN_NAME + time.timeInfo() + "\n" + outMessage + "\n");
			textField.setText("");
			textField.requestFocus();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JButton getBtn() {
		return button1;

	}

	public JTextArea getTa() {
		return textArea;
	}

	public static void main(String[] args) {
		SwingServerApp swingserver = new SwingServerApp("StrangeChat Server", 300, 400);
		swingserver.setSocketServer();

	}

}