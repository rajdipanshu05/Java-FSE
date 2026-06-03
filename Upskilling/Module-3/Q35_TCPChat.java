// Q35 - TCP Client-Server Chat
// Run TCPServer first, then TCPClient in a separate terminal.

import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out    = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println("Client: " + msg);
            System.out.print("Server: ");
            out.println(console.readLine());
        }

        socket.close();
        serverSocket.close();
    }
}

class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        PrintWriter out    = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String msg;
        while (true) {
            System.out.print("Client: ");
            msg = console.readLine();
            out.println(msg);
            System.out.println("Server: " + in.readLine());
        }
    }
}

public class Q35_TCPChat {
    public static void main(String[] args) {
        System.out.println("Run TCPServer and TCPClient as separate programs.");
        System.out.println("Compile both and run in different terminals.");
    }
}
