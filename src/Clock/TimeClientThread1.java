package Clock;

import java.io.DataInputStream;
import java.net.Socket;

public class TimeClientThread1 extends Thread {
    TimeClient1 timeClient1;
    Socket socket;
    DataInputStream din ;

    public TimeClientThread1(Socket socket, TimeClient1 timeClient1) {
        // TODO Auto-generated constructor stub
        this.socket = socket;
        this.timeClient1 = timeClient1;
    }
    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            while(true) {
                String time = din.readUTF();
                TimeClient1.appendTime(time+"\n");
                System.out.println(time);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
