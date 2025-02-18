
# Chat Application

## Overview
This is a simple client-server chat application implemented in Java. The server listens for incoming client connections, handles messages sent from clients, and allows the server to send messages back to the connected clients.

## Files

### 1. **CreateServer.java**

```java
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Chat Server is running on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client connected");

            // Thread to read incoming messages from client
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

            // Server to send messages
            String serverMessage;
            while (true){
                serverMessage = serverInput.readLine();
                if (serverMessage.equalsIgnoreCase("exit")){
                    break;
                }
                out.println("Server: " + serverMessage);
            }

            clientSocket.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
```

### 2. **ClientChat.java**

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

            // Thread to read incoming messages from server
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

            // Send messages from client to server
            String userMessage;
            while ((userMessage = userInput.readLine()) != null){
                out.println(userMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

## How It Works

1. **Server Side (CreateServer.java)**
   - The server listens on port `5000`.
   - Once a client connects, it starts a new thread to listen for incoming messages from the client.
   - The server can send messages back to the client through the same connection.

2. **Client Side (ClientChat.java)**
   - The client connects to the server at `localhost` and port `5000`.
   - The client can send messages to the server.
   - The client listens for messages from the server on a separate thread, which are displayed on the console.

## Running the Application

1. **Start the Server:**
   - Compile and run the `CreateServer.java` file on the terminal.
   - The server will be listening on port `5000`.

2. **Start the Client:**
   - Compile and run the `ClientChat.java` file on another terminal.
   - The client will connect to the server and allow you to send messages.

## Additional Notes
- This implementation allows only one client to connect at a time. To handle multiple clients, you would need to implement a mechanism for handling multiple threads for each client.
- The server and client will run until the user inputs `exit` on the server-side to stop communication.

## License

MIT License

Copyright (c) 2025, Akash Maity

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
