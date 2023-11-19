package swing.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ContentPaneFrame extends JFrame {

	public ContentPaneFrame(String title, int width, int height) {			//상속 생성자
		setTitle(title);					//(변수에 타이틀, 사이즈 넣고 아래 생성자에 글자쓰면 타이틀됨)
		setSize(width, height);
//		setTitle("깨깨오똑");		//타이틀 (아래 생성자에 넣어도 타이틀됨)
//		setSize(400, 500);		//사이즈
		setLocation(300, 300);	//창이 화면에 뜨는 위치 좌표조정
		setLocationRelativeTo(this);	//창위치 모니터가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//X눌렀을때 콘솔(응용프로그램)이 완전히 꺼지게 한다
		
		//보더레이아웃(기본)은 버튼이 무조건 가운데에 들어오고 크다
		//플로우레이아웃은 우리가 아는 버튼이 된다
		setLayout(new FlowLayout());
		
//		getContentPane().setBackground(Color.ORANGE);
		Container c = getContentPane();	//위랑 같음
		c.setBackground(Color.ORANGE);
		
		JButton b1 = new JButton("OK");	//컴포넌트
										//()버튼텍스트
		b1.setBackground(Color.RED);
		c.add(b1);					//(보더)버튼추가하면 항상 가운데에 생성됨
		
		JButton b2 = new JButton("Cancle");
		c.add(b2);		//c. 없이 바로 add쓰면 프레임 자체에 추가
		
		JButton b3 = new JButton("보내기");
		c.add(b3);
		
		
		
		
		setVisible(true);		//보이고싶다 트루
		
		
	}
	
	public static void main(String[] args) {
		new ContentPaneFrame("깨깨오똑", 400, 500);	//생성자 변수를 넣지 않는 이유는
								//여기에서만 쓸거면 안넣어도됨
		
		
	}

}
