package Box_chat_remake2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client_Thread extends Thread {

    String userName;
    Socket socket;
    client_Interface client_interface;

    DataOutputStream dout;
    DataInputStream din;

    Login l;

    public client_Thread(Socket socket, String userName, client_Interface client_interface){
        this.socket = socket;
        this.userName = userName;
        this.client_interface = client_interface;
    }

    public void sendMessage(String msg){
        try {
            dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("["+userName+"]: "+msg+"\n");
            dout.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout= new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("["+userName+"] đã vào phòng chat"+"\n");
            while(true){
                try {
                    Thread.sleep(1);
                    String message = din.readUTF();
                    client_interface.appearMessage(message+"\n");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
