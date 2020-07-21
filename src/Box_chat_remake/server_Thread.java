package Box_chat_remake;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class server_Thread extends Thread {

    DataInputStream din;
    DataOutputStream dout;

    Socket socket;
    server_Main serverMain;

    public server_Thread(Socket socket, server_Main serverMain){
        this.socket = socket;
        this.serverMain = serverMain;
    }

    public void sendMessageToAll(String message){
        for (server_Thread serverThread:serverMain.listServerThreads) {
                try {
                    if(serverThread.socket.isBound()){
                    serverThread.dout.writeUTF(message);
                    serverThread.dout.flush();
                    }
                }catch (Exception e){
                    try {
                        serverThread.din.close();
                        serverThread.dout.close();
                    }catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            while (true){
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                String msg = din.readUTF();
                sendMessageToAll(msg);
                server_Interface.appendMessage(msg);
            }
        }catch (SocketException e){
            try {
                din.close();
                dout.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
