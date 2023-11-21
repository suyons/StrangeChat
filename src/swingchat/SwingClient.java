package swingchat;

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

public class SwingClient extends JFrame implements ActionListener {
	// 폼 요소 정의
	private JPanel panelCenter;
	private JPanel panelSouth;

	// TextField: 단일 행 입출력 가능 → 신규 대화 입력 영역
	private JTextField textField;
	private JButton button1;
	private JButton button2;

	// TextArea: 여러 행 입출력 가능 → 대화 내용 보기 영역
	private JTextArea textArea;

	private Socket socket = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;

	// 생성자를 통한 폼 생성
	public SwingClient(String title, int width, int height) {
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
		JScrollPane sp = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		panelCenter.add(sp);
		add(panelCenter, BorderLayout.CENTER);
	}

	// 전송 버튼 정의
	private void setSouth() {
		panelSouth = new JPanel();
		textField = new JTextField(18);
		textField.addActionListener(this);
		panelSouth.add(textField);

		button1 = new JButton("전송");
		button1.addActionListener(this);

		button2 = new JButton("전송");
		button2.addActionListener(this);

		panelSouth.add(button1);

		add(panelSouth, BorderLayout.SOUTH);
	}

	// ActionListener 인터페이스의 actionPerformed 메서드 재정의
	@Override
	// When the action event occurs, that object's actionPerformed method is invoked.
	public void actionPerformed(ActionEvent e) {
		// action event가 발생한 source가 button1 또는 textfield 인가요?
		Object obj = e.getSource();
		if (obj == button1 || obj == textField) {
			localType();
		} else if (obj == button2) {
		}
	}

	private void localType() {
		try {
			// textfield에 있는 메세지를 보낼 거에요
			String outMessage = textField.getText();
			writer.write(outMessage + "\n"); // try캐치문 실행
			writer.flush();

			// textArea.append("[클라이언트] : " + time.timeInfo()+ "\n" + outMessage + "\n");
			textField.setText("");
			textField.requestFocus();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JButton getButton1() {
		return button1;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setSocket() {
		try {
			Time time = new Time();

			socket = new Socket("localhost", 9999);
			textArea.append(">>연결 완료!!!\n");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) { // 와일문 돌면서 뭔가를 받으면 찍겠다
				String inMsg = reader.readLine();
				textArea.append("[서버]  " + time.timeInfo() + "\n" + inMsg + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		SwingClient cc = new SwingClient("채팅클라이언트", 300, 400);
		cc.setSocket();
	}

}