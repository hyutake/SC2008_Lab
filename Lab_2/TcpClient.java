package Lab_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    private static final int PORT = 8001;
    private static final String HOST_IP_ADDRESS = "172.21.150.37";

    private static byte[] buffer = new byte[2048];
    public static void main(String[] args) {
        // 1. Establish TCP connection w. server
        Socket socket = null;
        try {
            InetAddress address = InetAddress.getByName(HOST_IP_ADDRESS);
            socket = new Socket(address, PORT);
        } catch(Exception  e) {
            System.out.println("TcpClient exception: " + e.getMessage());
            e.printStackTrace();
        }

        String message = "Hello!";
        buffer = message.getBytes();

        try {
            // 2. Send TCP request to server
            OutputStream os = socket.getOutputStream();
            os.write(buffer);

            // 3. Receive TCP reply from server
            buffer = new byte[2048];    // refresh buffer
            InputStream is = socket.getInputStream();
            is.read(buffer);

            String reply = new String(buffer);
            System.out.println("Reply: " + reply);
        } catch(IOException e) {
            System.out.println("TcpClient IOException: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
