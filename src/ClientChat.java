import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientChat {
    private static final String server_address = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args){
        try (Socket socket = new Socket(server_address, PORT)){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to server. Type your message: ");

            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null){
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected.");
                }
            }).start();

            String userMessage;
            while ((userMessage = userInput.readLine()) != null){
                out.println(userMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
