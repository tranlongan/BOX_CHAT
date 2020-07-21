package Clock;

import java.io.DataInputStream;
import java.net.Socket;

public class TimeClientThread2 extends Thread{
    TimeClient2 timeClient2;
    Socket socket;
    DataInputStream din ;

    public TimeClientThread2(Socket socket, TimeClient2 timeClient2) {
        // TODO Auto-generated constructor stub
        this.socket = socket;
        this.timeClient2 = timeClient2;
    }
    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            while(true) {
                String time = din.readUTF();
                TimeClient2.appendTime(time+"\n");
                System.out.println(time);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
