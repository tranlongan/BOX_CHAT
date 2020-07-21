package Box_chat_remake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client_Thread1 extends Thread{

    Socket socket;
    String username;
    client_Interface1 clientInterface1;

    DataOutputStream dout;
    DataInputStream din;

    public client_Thread1(Socket socket, String username, client_Interface1 clientInterface1){
            this.socket =  socket;
            this.username = username;
            this.clientInterface1 = clientInterface1;
    }

    public void sendMessage(String msg){
        try {
            dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("["+username+"]"+msg+"\n");
            dout.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("["+username+"] đã vào phòng chat"+"\n");
            while (true){
                try {
                    Thread.sleep(1);
                    String mess = din.readUTF();
                    clientInterface1.appearMessage(mess+"\n");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
