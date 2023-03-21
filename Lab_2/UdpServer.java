package Lab_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

// Receives packages and responds to them
public class UdpServer {
    private static final int PORT_NUMBER = 8000;
    private static final int BUFFER_LENGTH = 2048;

    private static byte[] buffer = new byte[BUFFER_LENGTH];
    public static void main(String[] args) {
        // 1. Open UDP Socket
        DatagramSocket socket = null;
        try {
            // Get local address
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Server address: " + address.toString());

            // Initialise a server socket for local address w/ PORT_NUMBER
            socket = new DatagramSocket(PORT_NUMBER, address);
        } catch(SocketException e) {
            System.out.println("Rfc865UdpServer SocketException: " + e.getMessage());
            System.out.println("Stack trace: " + e.getStackTrace());
            return; // exit if there is an exception
        } catch(UnknownHostException e) {
            System.out.println("Rfc865UdpServer UnknownHostException: " + e.getMessage());
            System.out.println("Stack trace: " + e.getStackTrace());
            return; // exit if there is an exception
        }

        System.out.println("Listening for requests...");

        while(true) {
            try {
                // 2. Listen for UDP request from client
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                // "Interpret" the received request
                String receivedMsg = new String(request.getData(), request.getOffset(), request.getLength());
                System.out.println("Message from client: " + receivedMsg);

                // 3. Send UDP reply to client
                // Get client's address
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                // Create reply
                String replyMsg = "devve received \"" + receivedMsg + "\"";
                // String replyMsg = "devve received \"" + receivedMsg + "\" from clientAddress: " + clientAddress.toString(); // checking local client address
                buffer = replyMsg.getBytes();
                // Send reply
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                socket.send(reply);

                buffer = new byte[BUFFER_LENGTH];   // reset the buffer
            } catch(IOException e) {
                System.out.println("Rfc865UdpServer IOException: " + e.getMessage());
                System.out.println("Stack trace: " + e.getStackTrace());
                break;  // exit if there is an exception
            }
        }
        if(socket != null) socket.close();
    }
}
