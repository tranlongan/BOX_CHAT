package Box_chat_remake;

import javax.swing.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class server_Main {

    ServerSocket serverSocket;
    server_Interface si;


    static ArrayList<server_Thread> listServerThreads = new ArrayList<>();

    public server_Main(int port){
        try {
            serverSocket = new ServerSocket(port);
            si = new server_Interface(port);
            while (true){
               Socket socket = serverSocket.accept();
               server_Thread st = new server_Thread(socket,server_Main.this);
               st.start();
               listServerThreads.add(st);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
       try {
           int port = 0;
           String s = JOptionPane.showInputDialog(null,"Hãy cấp phát cổng server",
                   "",JOptionPane.QUESTION_MESSAGE);
           try {
               port = Integer.parseInt(s);
           }catch (NumberFormatException e){
               JOptionPane.showMessageDialog(null,"Port là kiểu Integer",
                       "ERROR",JOptionPane.ERROR_MESSAGE);
           }
           new server_Main(port);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
