package Box_chat_remake1;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class thread_server extends Thread {

    Socket socket;
    main_server mainServer;
    client_Frame clientFrame;

    ObjectOutputStream dout;
    ObjectInputStream din;

    Boolean test = true;

    public thread_server(Socket socket, main_server mainServer) {
        this.socket = socket;
        this.mainServer = mainServer;
    }

    public void sendMessageToAllClient(String message) {
        for (thread_server threadServer : mainServer.thread_servers) {
            try {
                if (threadServer.socket.isBound()) {
                    threadServer.dout.writeUTF(message);
                    threadServer.dout.flush();
                }
            } catch (IOException e) {
                try {
                    threadServer.din.close();
                    threadServer.dout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    public void saveFile(File file) {
        try {
            din = new ObjectInputStream(socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int filesize = 99999; // Send file size in separate msg
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = din.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            din = new ObjectInputStream(socket.getInputStream());
            dout = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                try {
                    Message ms = (Message) din.readObject();
                    server_Frame.appendMessage(ms.getContentMessage());
                    sendMessageToAllClient(ms.getContentMessage());


                    if (ms.getFile() != null) {
//                        String a = "D:\\CBl.txt";
//                        File newF = new File(a);
//                        saveFile(newF);
                        server_Frame.appendMessage("Coo");
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (SocketException e) {
            try {
                din.close();
                dout.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
