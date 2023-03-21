package Lab_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

// Sends requests to other servers and then receives their responses
public class LabUdpClient {
    private static final String LAB_SERVER_NAME = "hwlab1.scse.ntu.edu.sg";
    private static final String LAB_SERVER_IP_ADDRESS = "172.21.14.201"; // To be filled
    private static final int LAB_SERVER_PORT_NUMBER = 17;

    private static final String CLIENT_IP_ADDRESS = "172.21.150.37";
    private static final int CLIENT_PORT_NUMBER = 8002;

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

        // String message = "MYNAME, LABGROUP, IPADDRESS"; // anonymous test :>
        String message = MY_NAME + ", " + LAB_GROUP + ", " + CLIENT_IP_ADDRESS;
        buffer = message.getBytes();
        
        try {
            // 2. Send UDP request to server
            InetAddress serverAddress = InetAddress.getByName(LAB_SERVER_NAME); // Get server address as InetAddress type
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, serverAddress, LAB_SERVER_PORT_NUMBER);
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
