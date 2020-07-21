package Box_chat_remake2;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class client_Interface extends JFrame {
    JPanel mainPanel,mainImg, outpPanel, outpPanel1, outpPanel2, inpPanel, addressPanel;
    JTextArea txaOup, txaInp, txaOup1;
    static JButton send, connect;
    JLabel Messenger, lbTn, lbFile,lbImg,lbTitleImg;
    JTextField tfLocalhost, tfPort;
    JLabel lbIp, lbPort;
    JScrollPane jScrollPane;
    Login lg;

    client_Thread clientThread;
    Socket socket;

    static ArrayList<client_Thread> listThreads = new ArrayList<>();

    public client_Interface(String username) {
        this.setTitle("Client chat 1");
        this.setLocation(50, 80);
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
                    socket = new Socket(adress, port);
                    clientThread = new client_Thread(socket, username, client_Interface.this);
                    clientThread.start();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "port là kiểu Integer",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });

        // Ảnh
        mainImg = new JPanel();
        ImageIcon img = new ImageIcon("img/images.png");
        Image dabImage = img.getImage();
        Image setImg = dabImage.getScaledInstance(165,75, Image.SCALE_SMOOTH);
        img = new ImageIcon(setImg);
        lbImg = new JLabel(img);
        lbTitleImg = new JLabel("WELCOME TO MY BOX CHAT");
        mainImg.add(lbImg);
        mainImg.add(lbTitleImg);
        mainImg.setBorder(BorderFactory.createEmptyBorder(0,-330,0,0));
        // Hiển thị nội dung chat
        outpPanel = new JPanel();
        outpPanel.setLayout(new BoxLayout(outpPanel, BoxLayout.X_AXIS));
        outpPanel.setPreferredSize(new Dimension(700, 200));
        outpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outpPanel1 = new JPanel();
        outpPanel1.setLayout(new BoxLayout(outpPanel1, BoxLayout.Y_AXIS));
        outpPanel1.setPreferredSize(new Dimension(550, 200));
        outpPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        outpPanel2 = new JPanel();
        outpPanel2.setLayout(new BoxLayout(outpPanel2, BoxLayout.Y_AXIS));
        outpPanel2.setPreferredSize(new Dimension(150, 200));


        lbTn = new JLabel("Tin nhắn đã gửi");
        txaOup = new JTextArea();
        txaOup.setEditable(false);
        lbFile = new JLabel("Account is using");
        jScrollPane = new JScrollPane();
        txaOup1 = new JTextArea();
        txaOup1.append(username);
        txaOup1.setEditable(false);
        jScrollPane.setViewportView(txaOup);
        outpPanel1.add(lbTn);
        outpPanel1.add(jScrollPane);
        outpPanel2.add(lbFile);
        outpPanel2.add(txaOup1);
        outpPanel.add(outpPanel1);
        outpPanel.add(outpPanel2);
        // Nhập nội dung chat
        inpPanel = new JPanel();
        inpPanel.setLayout(new BoxLayout(inpPanel, BoxLayout.X_AXIS));
        inpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        Messenger = new JLabel("MESSENGER ");
        txaInp = new JTextArea((Document) null);
        txaInp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        send = new JButton("SEND");
        send.setPreferredSize(new Dimension(150,20));
        inpPanel.add(Messenger);
        inpPanel.add(txaInp);
        inpPanel.add(send);
        // Thực hiện sự kiện nút send
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    String mess = txaInp.getText().trim();
                    if (mess.equals("")) {
                        System.out.println("Bạn muốn nhắn điều gì???");
                        txaInp.setText(null);
                    } else {
                        if (mess.equals("\n")) {
                            System.out.println("Bạn không có gì để nhắn cả!!!");
                            txaInp.setText(null);
                        } else {
                            txaInp.setText(null);
                            clientThread.sendMessage(mess);
                        }
                    }
                } catch (NullPointerException e1) {
                    System.out.println("Bạn chưa conecion với server");
                }
            }
        });
        // Khung chính
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainImg);
        mainPanel.add(outpPanel);
        mainPanel.add(inpPanel);
        this.add(addressPanel, BorderLayout.NORTH);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public void appearMessage(String message) {
        txaOup.append(message);
    }

    public static void main(String args[]) {
        new Login();
    }
}
