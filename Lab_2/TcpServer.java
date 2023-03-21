package Lab_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private static int PORT_NUMBER = 8001;
    public static void main(String[] args) {
        ServerSocket parentSocket = null;
        try {
            parentSocket = new ServerSocket(PORT_NUMBER);
        } catch(IOException e) {
            System.out.println("TcpServer IOException: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("parentSocket created on port " + PORT_NUMBER);
        System.out.println("Listening for requests...");

        while(true) {
            try{
                // 2. Listen to establish TCP connection w/ client
                Socket childSocket = parentSocket.accept(); // accept childSocket (from client)
                // 3. Create new thread to handle client connection
                ClientHandler client = new ClientHandler(childSocket);
                Thread thread = new Thread(client);
                thread.start();
            } catch(IOException e) {
                System.out.println("TcpServer ClientHandler IOException: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    ClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            // 4. Receive TCP request
            byte[] buffer = new byte[2048];
            InputStream is = socket.getInputStream();
            is.read(buffer);
            String request = new String(buffer);
            System.out.println("Request: " + request);

            // 5. Send TCP reply to client
            String reply = "Received request: " + request;
            buffer = reply.getBytes();
            OutputStream os = socket.getOutputStream();
            os.write(buffer);
            System.out.println("Reply sent!");
        } catch(IOException e) {
            System.out.println("TcpServer ClientHandler IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}