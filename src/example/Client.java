package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            // Client continuously sends messages to the server
            while (true) {
                // Get user input
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter message for server: ");
                String message = userInput.readLine();

                // Send the message to the server
                writer.println(message);

                // Receive and display the server's response
                String serverResponse = reader.readLine();
                System.out.println("Server: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
