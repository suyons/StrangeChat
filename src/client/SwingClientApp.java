package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Constants;

public class SwingClientApp extends JFrame implements ActionListener {
	// 폼 요소 정의
	private JPanel panelCenter;
	private JPanel panelSouth;

	// TextField: 단일 행 입출력 가능 → 신규 대화 입력 영역
	private JTextField textField;
	private JButton button1;

	// TextArea: 여러 행 입출력 가능 → 대화 내용 보기 영역
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JScrollBar scrollBar;

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private BufferedReader userInput;

	// 생성자를 통한 폼 생성
	public SwingClientApp(String title, int width, int height) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(800, 200);
		setSize(width, height);
		setLayout(new BorderLayout());

		setCenter();
		setSouth();
		setVisible(true);
		textField.requestFocus();
	}

	// 대화내용 표시하는 텍스트 영역 정의
	private void setCenter() {
		panelCenter = new JPanel();
		panelCenter.setBackground(Color.BLUE);
		panelCenter.setLayout(new BorderLayout());

		textArea = new JTextArea(7, 18);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = scrollPane.getVerticalScrollBar();

		panelCenter.add(scrollPane);
		add(panelCenter, BorderLayout.CENTER);
	}

	// 전송 버튼 정의
	private void setSouth() {
		panelSouth = new JPanel();
		textField = new JTextField(18);
		textField.addActionListener(this);
		panelSouth.add(textField);

		button1 = new JButton("보내기");
		button1.addActionListener(this);

		panelSouth.add(button1);

		add(panelSouth, BorderLayout.SOUTH);
	}

	/*
	 * ActionListener 인터페이스의 actionPerformed 메서드 재정의
	 * When the action event occurs, that object's actionPerformed
	 * method is invoked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// ActionListener를 통해 ActionEvent가 발생한 source가 button1 또는 textfield 인가요?
		Object obj = e.getSource();
		if (obj == button1 || obj == textField) {
			sendMessage();
		}
	}

	private void sendMessage() {
		// textField에 작성된 메시지 전송하기
		String content = textField.getText();
		// while ((content = textField.getText()).trim().isEmpty()) { }
		if (!content.equals(""))
			writer.println(content);

		// 메시지를 보내고 textField 내용 지우기
		textField.setText("");
		// Requests boolean result that this Component gets the input focus.
		textField.requestFocusInWindow();
	}

	public JButton getButton1() {
		return button1;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setSocket() {
		try {
			socket = new Socket(Constants.SERVER_ADDR, Constants.SERVER_PORT);
			reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			writer = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			userInput = new BufferedReader(
					new InputStreamReader(System.in, StandardCharsets.UTF_8));
			while (true) {
				try {
					textArea.append(reader.readLine() + "\n");
				} catch (SocketException e) {
					textArea.append(Constants.SYSTEM_NAME + "서버와의 연결에 실패했습니다.\n");
				}

				scrollBar.setValue(scrollBar.getMaximum());
			}
		} catch (ConnectException e) {
			textArea.append(Constants.SYSTEM_NAME + "서버와의 연결에 실패했습니다.\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
				socket.close();
				userInput.close();
			} catch (NullPointerException e) {
				textArea.append("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		SwingClientApp swingClient = new SwingClientApp("StrangeChat", 400, 400);
		swingClient.setSocket();
	}

}