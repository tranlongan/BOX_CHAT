package Box_chat_remake1;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class main_server {

    ServerSocket serverSocket;
    server_Frame serverFrame;
    thread_server threadServer;

    static ArrayList<thread_server> thread_servers = new ArrayList<>();

    public main_server(int port){
        try {
            serverSocket = new ServerSocket(port);
            serverFrame = new server_Frame(port);
            while (true){
                Socket socket = serverSocket.accept();
                threadServer = new thread_server(socket,main_server.this);
                threadServer.start();
                thread_servers.add(threadServer);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        try {
            int port = 0;
            String s = JOptionPane.showInputDialog(null,"Nhập cổng muốn cấp phát",
                    "",JOptionPane.QUESTION_MESSAGE);
            try {
                port = Integer.parseInt(s);
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Port là kiểu Integer",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            }
            new main_server(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
