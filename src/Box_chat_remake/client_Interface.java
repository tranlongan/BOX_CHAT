package Box_chat_remake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class client_Interface extends JFrame {
    JPanel mainPanel, outpPanel, inpPanel, addressPanel;
    JTextArea txaOup,txaInp;
    static JButton send, connect;
    JLabel Messenger;
    JTextField tfLocalhost, tfPort;
    JLabel lbIp, lbPort;
    JScrollPane jScrollPane;

    client_Thread clientThread;
    Socket socket;

    static ArrayList<client_Thread> listThreads = new ArrayList<>();

    public client_Interface(String username){
        this.setTitle("Client chat 1");
        this.setLocation(50,80);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Hiển thị IP và Port
        addressPanel = new JPanel();
            lbIp = new JLabel("IP");
            tfLocalhost = new JTextField("localhost");
                tfLocalhost.setColumns(10);
                tfLocalhost.setEditable(false);
            lbPort = new JLabel("PORT");
                tfPort = new JTextField("7500");
                tfPort.setColumns(10);
            connect = new JButton("CONNECT");
        addressPanel.add(lbIp);
        addressPanel.add(tfLocalhost);
        addressPanel.add(lbPort);
        addressPanel.add(tfPort);
        addressPanel.add(connect);

        // Thực hiện sự kiện nút connect
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int port = 0;
                String adress = tfLocalhost.getText();
                try {
                        port = Integer.parseInt(tfPort.getText());
                        socket = new Socket(adress,port);
                        clientThread = new client_Thread(socket,"Name_1",client_Interface.this);
                        clientThread.start();
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"port là kiểu Integer",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                }catch (IOException e2){
                    e2.printStackTrace();
                }
            }
        });
        // Hiển thị nội dung chat
        outpPanel = new JPanel();
        outpPanel.setLayout(new BoxLayout(outpPanel,BoxLayout.X_AXIS));
        outpPanel.setPreferredSize(new Dimension(500,200));
        outpPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            txaOup = new JTextArea();
            jScrollPane = new JScrollPane();
                jScrollPane.setViewportView(txaOup);
        outpPanel.add(jScrollPane);
        // Nhập nội dung chat
        inpPanel = new JPanel();
        inpPanel.setLayout(new BoxLayout(inpPanel,BoxLayout.X_AXIS));
        inpPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            Messenger = new JLabel("MESSENGER ");
            txaInp = new JTextArea();
            send = new JButton(">>");
        inpPanel.add(Messenger);
        inpPanel.add(txaInp);
        inpPanel.add(send);
        // Thực hiện sự kiện nút send
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    String mess = txaInp.getText();
                    if(mess.equals("")){
                        System.out.println("Bạn muốn nhắn điều gì???");
                    }
                    else {
                        clientThread.sendMessage(mess);
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        // Khung chính
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(outpPanel);
        mainPanel.add(inpPanel);
        this.add(addressPanel,BorderLayout.NORTH);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }
    public void appearMessage(String message){
        txaOup.append(message);
    }

    public static void main(String args[]){
        new client_Interface("Name_1");
    }
}
