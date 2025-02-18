import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class CreateServer {
    private static final int PORT = 5000;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat Server is running on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client connected");

            new Thread(() -> {
                String message;
                while (true){
                    try {
                        if ((message = in.readLine()) != null) {
                            System.out.println("Client message: " + message);
                        }
                    } catch (IOException e) {
                        System.out.println("Client got disconnected");
                        break;
                    }
                }
            }).start();

            String serverMessage;
            while (true){
                serverMessage = serverInput.readLine();
                if (serverMessage.equalsIgnoreCase("exit")){
                    break;
                }
                out.println("Server: " + serverMessage);
            }

            clientSocket.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}