package Box_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ConcurrentModificationException;

public class ServerThread extends Thread {

    Socket socket;
    Server server;
    DataInputStream din;
    DataOutputStream dout;

    public ServerThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public static void sendMessageToAllClient(String message) {
        for (ServerThread st : Server.connections) {
            try {
                if (st.socket.isBound()) {
                    st.dout.writeUTF(message);
                    st.dout.flush();
                }
            }
            catch (ConcurrentModificationException e) {
                Server.connections.clear();
            }
            catch (SocketException e) {
                try {
                    st.din.close();
                    st.dout.close();
                    st.socket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Server.connections.remove(st);
            }

            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                String message = din.readUTF();

                ServerConsole.appendMessage(message);
                sendMessageToAllClient(message);

            }
        }
        catch (SocketException e) {
            try {
                din.close();
                dout.close();
                socket.close();
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        catch (EOFException e) {
            System.out.println("");
        }
        catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
