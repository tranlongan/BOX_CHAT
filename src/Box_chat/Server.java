package Box_chat;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.ArrayList;

public class Server {

    public static ServerSocket ss;
    ServerConsole sc;
    public static Connection connection = null;
    public static ArrayList<ServerThread> connections = new ArrayList<ServerThread>();

    public static void main(String[] args) {
        try {
            String s = JOptionPane.showInputDialog(null, "Please input server port!", "", JOptionPane.QUESTION_MESSAGE);
            int port = 0;
            try {
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(null, "Port number must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            new Server(port);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
            sc = new ServerConsole(port);
            sc.setVisible(true);
            while (true) {
                Socket socket = ss.accept();
                ServerThread serverThread = new ServerThread(socket, this);
                serverThread.start();
                connections.add(serverThread);
            }
        } catch (BindException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Port already in use!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (SocketException e) {
            ServerConsole.appendMessage("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

