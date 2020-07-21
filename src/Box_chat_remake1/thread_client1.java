package Box_chat_remake1;

import java.io.*;
import java.net.Socket;

public class thread_client1 extends Thread {

    ObjectInputStream din1;
    ObjectOutputStream dout1;
    InputStream is;
    FileOutputStream fos;
    BufferedOutputStream bos;

    Socket socket;
    String username, file;
    client_Frame clientFrame;

    public thread_client1(Socket socket, String username, client_Frame  clientFrame){
        this.socket = socket;
        this.username = username;
        this.clientFrame = clientFrame;
    }

    public void senMessage(String message){
        try {
            dout1 = new ObjectOutputStream(socket.getOutputStream());
            dout1.writeObject(new Message(message, null));
            dout1.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void senFile(File fileTxt){
        try {
            FileInputStream fis = new FileInputStream(fileTxt);
            byte[] buffer = fis.readAllBytes();

            Message message = new Message(fileTxt.getName(), buffer);

            dout1.writeObject(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            din1 = new ObjectInputStream(socket.getInputStream());
            dout1 = new ObjectOutputStream(socket.getOutputStream());
            dout1.writeUTF("["+username+"] đã vào phòng chat"+"\n");
                try {
                    while (true){
                        Thread.sleep(1);
                        String messaggg = din1.readUTF();
                        clientFrame.appearTinNhan(messaggg+"\n");
//                        clientFrame.appearTinNhan("b47.txt\n");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
