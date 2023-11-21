package swingchat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

// Thread implements Runnable.
// 따라서 "extends Thread"는 "implements Runnable"을 포함한다.
public class ServerThread extends Thread {
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threadList) {
		this.socket = socket;
		this.threadList = threadList;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String inMsg = null;
			
			while(true) {				//소켓을통해서 연결된 애를 받으면
				inMsg = in.readLine();
				System.out.println("받은 메시지: "+ inMsg);
				if(inMsg.equalsIgnoreCase("bye")) {
					break;
				}
				sendToAllClient(inMsg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 서버스레드가 내가 보낼놈
	private void sendToAllClient(String outMsg) {
		for (ServerThread st : threadList) {	//나한테 연결된 모든 애한테 다뿌린다는 의미
			try {
				st.out.write(outMsg + "\n");
				st.out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}









