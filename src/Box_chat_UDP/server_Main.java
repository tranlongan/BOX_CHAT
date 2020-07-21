package Box_chat_UDP;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class server_Main {
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;

    server_Thread serverThread;
    server_Frame serverFrame;
    static ArrayList<DatagramSocket> list = new ArrayList<>();

    public server_Main(int port){
        try {
            serverFrame = new server_Frame(port);
            serverThread = new server_Thread(datagramSocket,this);
        }catch (Exception e){
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
            new server_Main(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
