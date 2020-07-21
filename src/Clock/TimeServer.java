package Clock;

import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    TimeServerThread serverThread;
    Socket socket;

    public TimeServer() {
        try {
            ServerSocket server = new ServerSocket(7000);
            System.out.println("Server is started");
            while(true) {
                socket = server.accept();
                serverThread = new TimeServerThread(socket, this);
                serverThread.start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        new TimeServer();
    }
}
