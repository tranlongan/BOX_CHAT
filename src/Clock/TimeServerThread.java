package Clock;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;

public class TimeServerThread extends Thread {
    Socket socket;
    DataOutputStream dos;
    TimeServer timeServer;

    public TimeServerThread(Socket socket, TimeServer timeServer) {
        this.socket = socket;
        this.timeServer = timeServer;
    }
    @Override
    public void run() {
        try {

            dos = new DataOutputStream(socket.getOutputStream());
            while(true) {
                Thread.sleep(1000);
                String time = new Date().toString();
                dos.writeUTF("Server tra lai ngay gio: " +time);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
