package swing.frame;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	public MyFrame(String title, int width, int height) {			//상속 생성자
		setTitle(title);					//(변수에 타이틀, 사이즈 넣고 아래 생성자에 글자쓰면 타이틀됨)
		setSize(width, height);
//		setTitle("깨깨오똑");		//타이틀 (아래 생성자에 넣어도 타이틀됨)
//		setSize(400, 500);		//사이즈
		setLocation(300, 300);	//창이 화면에 뜨는 위치 좌표조정
		setLocationRelativeTo(this);	//창위치 모니터가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//X눌렀을때 콘솔이 완전히 꺼지게 한다
									//
		setVisible(true);		//보이고싶다 트루
		
		
	}
	
	public static void main(String[] args) {
		new MyFrame("Title", 400, 500);	//생성자 변수를 넣지 않는 이유는
								//여기에서만 쓸거면 안넣어도됨
		
		
	}

}
