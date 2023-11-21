package donotuse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import common.Constants;
public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Server continuously listens for messages from the client
            while (true) {
                String clientMessage = reader.readLine();
                if (clientMessage == null) {
                    break;  // Exit the loop if the client disconnects
                }
                System.out.println("Client: " + clientMessage);

                // Server sends a response back to the client
                writer.println("Server received: " + clientMessage);
            }
        } catch (ConnectException e) {
            System.out.println(Constants.SYSTEM_NAME + "서버를 찾지 못했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
