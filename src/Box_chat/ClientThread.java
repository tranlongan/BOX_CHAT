package Box_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread {

    Socket socket;
    String username;
    ChatRoom client;
    public DataInputStream din;
    public DataOutputStream dout;

    public ClientThread(Socket socket, String username, ChatRoom client) {
        this.socket = socket;
        this.username = username;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("\"" + username + "\" Has Joined The Chat! hihi\n");

            while (true) {
                try {
                    Thread.sleep(1);
                    String message = din.readUTF();
                    client.appearMessage(message + "\n");
                } catch (SocketException e1) {
                    System.exit(0);
                } catch (EOFException e1) {
                    System.exit(0);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(username+": "+msg + "\n");
            dout.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
