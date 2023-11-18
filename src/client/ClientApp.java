package client;

import java.io.IOException;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            // Handle client operations, such as sending/receiving messages

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
