package servertest;

import java.net.*;
import java.io.*;

public class ChatClient {
	 private Socket socket;
	    private BufferedReader in;
	    private PrintWriter out;

	    public ChatClient(String host, int port) throws IOException {
	        socket = new Socket(host, port);
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);
	    }

	    public void sendMessage(String message) {
	        out.println(message);
	    }

	    public void run() {
	        try {
	            while (true) {
	                String message = in.readLine();
	                System.out.println(message);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) throws IOException {
	        String host = "localhost";
	        int port = 5000;
	        ChatClient client = new ChatClient(host, port);
	        client.run();
	    }
}
