package Box_chat_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class server_Thread extends Thread {
    DatagramPacket datagramPacket;
    DatagramSocket datagramSocket;

    server_Main serverMain;

    ArrayList<DatagramSocket> list  = new ArrayList<>();

    public server_Thread(DatagramSocket datagramSocket, server_Main serverMain){
        this.datagramSocket = datagramSocket;
        this.serverMain = serverMain;
    }

    public void sendMessageToAllClient(String msg){
        for (DatagramSocket thread: server_Main.list  ) {
            try {
                    if(thread.isBound()){
                        byte[] tinNhan = msg.getBytes();
                        int leng = tinNhan.length;
                        InetAddress add = InetAddress.getByName("localhost");
                        datagramPacket = new DatagramPacket(tinNhan,leng,add,7500);
                        thread.send(datagramPacket);
                    }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run()  {
        try {
            while (true){
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                byte[] receiData = new byte[1024];
                int leng = receiData.length;
                datagramPacket = new DatagramPacket(receiData,leng);
                datagramSocket.receive(datagramPacket);
                String ch = (new String(receiData,receiData.length)).trim();
                sendMessageToAllClient(ch);
                server_Frame.appendMessage(ch);
            }
        }catch (Exception e){
            datagramSocket.close();
        }
    }
}
