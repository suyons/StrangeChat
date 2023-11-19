package client;

import java.io.IOException;
import java.net.Socket;
import common.*;

public class ClientApp {
    public static void main(String[] args) {
        try (Socket socket = new Socket(Constants.SERVER_ADDR, Constants.SERVER_PORT)) {
            // Creates a stream socket and connects it to the specified port number at the
            // specified IP address.
            System.out.println("Connected to server.");

            // Handle client operations, such as sending/receiving messages

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
