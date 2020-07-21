package Box_chat_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class client_Thread extends Thread {

    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;
    byte[] chuoiGui, chuoiNhan;
    InetAddress address;
    client_Frame clientFrame;

    public client_Thread(DatagramSocket datagramSocket, client_Frame clientFrame){
        this.datagramSocket = datagramSocket;
        this.clientFrame = clientFrame;
    }

    public void sendMessage(String messsage){
        try {
            chuoiGui = messsage.getBytes();
            int lg = chuoiGui.length;
            address = InetAddress.getByName("localhost");
            datagramPacket = new DatagramPacket(chuoiGui,lg,address,7500);
            datagramSocket.send(datagramPacket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            chuoiNhan = new  byte[1024];
            int lg = chuoiNhan.length;
            while (true){
                Thread.sleep(1);
                datagramPacket  = new DatagramPacket(chuoiNhan,lg);
                datagramSocket.receive(datagramPacket);
                String tinNhanNhanVe = (new String(chuoiNhan,chuoiNhan.length)).trim();
                clientFrame.appearTinNhan(tinNhanNhanVe);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
