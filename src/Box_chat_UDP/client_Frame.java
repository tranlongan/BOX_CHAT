package Box_chat_UDP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;

public class client_Frame extends JFrame {
    JPanel mainPanel, outpPanel, inpPanel, addressPanel;
    JTextArea txaOup,txaInp;
    static JButton send, connect;
    JLabel Messenger;
    JTextField tfLocalhost, tfPort;
    JLabel lbIp, lbPort;
    JScrollPane jScrollPane;

    client_Thread clThr;
    DatagramSocket datagramSocket;

    public client_Frame(String username){
        this.setTitle("Client chat 1");
        this.setLocation(880,80);
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
                    datagramSocket = new DatagramSocket();
                    clThr = new client_Thread(datagramSocket,client_Frame.this);
                    clThr.start();
                    String tn = txaOup.getText();
                    clThr.sendMessage(tn);
                } catch (SocketException socketException) {
                    socketException.printStackTrace();
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

    public void appearTinNhan(String tn){
        txaOup.append(tn);
    }

    public static void main(String args[]){
        new client_Frame("Name_1");
    }
}
