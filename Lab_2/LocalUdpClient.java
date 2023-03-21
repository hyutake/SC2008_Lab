package Lab_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

// Sends requests to other servers and then receives their responses
public class LocalUdpClient {

    private static final String NEIGHBOUT_IP_ADDRESS = "172.21.145.108";

    private static final String LOCAL_SERVER_NAME = "hwl1-vb04";
    private static final String LOCAL_SERVER_IP_ADDRESS = "172.21.150.37"; // IPV4 Address (ntu hwlab1 b04)
    private static final int LOCAL_SERVER_PORT_NUMBER = 8000;

    private static final int CLIENT_PORT_NUMBER = 8003;

    private static final int BUFFER_LENGTH = 2048;
    private static final String MY_NAME = "Cai Kaihang";
    private static final String LAB_GROUP = "A33";

    private static byte[] buffer;
    public static void main(String[] args) {
        // 1. Open UDP Socket
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(CLIENT_PORT_NUMBER);
        } catch(SocketException e) {
            System.out.println("Rfc865UdpClient SocketException: " + e.getMessage());
            System.out.println("Stack trace: " + e.getStackTrace());
            return;
        }

        String message = MY_NAME + ", " + LAB_GROUP;
        buffer = message.getBytes();
        
        try {
            // 2. Send UDP request to server
            InetAddress serverAddress = InetAddress.getByName(LOCAL_SERVER_IP_ADDRESS); // Get server address as InetAddress type
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, serverAddress, LOCAL_SERVER_PORT_NUMBER);
            clientSocket.send(request);

            // 3. Receive UDP reply from server
            buffer = new byte[BUFFER_LENGTH];   
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(reply);
            // "Interpret" the received reply
            String receivedMsg = new String(reply.getData(), reply.getOffset(), reply.getLength());
            System.out.println("Message from server: " + receivedMsg);
        } catch(IOException e) {
            System.out.println("Rfc865UdpClient IOException: " + e.getMessage());
            System.out.println("Stack trace: " + e.getStackTrace());
        }
        if(clientSocket != null) clientSocket.close();
    }
}
