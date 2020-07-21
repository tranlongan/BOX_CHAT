package Box_chat_remake1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class client_Frame extends JFrame {

    JPanel mainPanel, outpPanel, outpPanel1, outpPanel2, inpPanel, addressPanel;
    JTextArea txaOup, txaOup1, txaInp;
    static JButton send, connect, open, openFileTxt, openFileImage;
    JLabel Messenger;
    JTextField tfLocalhost, tfPort;
    JLabel lbIp, lbPort, lbTn, lbFile;
    JScrollPane jScrollPane;
    String adr;
    Boolean test=true ;

    public static boolean tT(Boolean test){
        if(test==true){
            return true;
        }
        else {
            return false;
        }
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    JFileChooser fc;
    File selectedFile;

    Socket socket;
    thread_client1 thcl1;


    public client_Frame(String username) {
        this.setTitle("Client chat 1");
        this.setLocation(880, 80);
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
                String adress = tfLocalhost.getText().toString();
                try {
                    port = Integer.parseInt(tfPort.getText());
                    socket = new Socket(adress, port);
                    thcl1 = new thread_client1(socket, "Name_1", client_Frame.this);
                    thcl1.start();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Port là kiểu Integer",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        // Hiển thị nội dung chat
        outpPanel = new JPanel();
        outpPanel.setLayout(new BoxLayout(outpPanel, BoxLayout.X_AXIS));
        outpPanel.setPreferredSize(new Dimension(500, 200));
        outpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outpPanel1 = new JPanel();
        outpPanel1.setLayout(new BoxLayout(outpPanel1, BoxLayout.Y_AXIS));
        outpPanel1.setPreferredSize(new Dimension(350, 200));
        outpPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        outpPanel2 = new JPanel();
        outpPanel2.setLayout(new BoxLayout(outpPanel2, BoxLayout.Y_AXIS));
        outpPanel2.setPreferredSize(new Dimension(150, 200));
        outpPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        lbTn = new JLabel("Tin nhắn đã gửi");
        txaOup = new JTextArea();
        lbFile = new JLabel("File đã gửi & đã nhận");
        jScrollPane = new JScrollPane();
        txaOup1 = new JTextArea();
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
        inpPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        Messenger = new JLabel("MESSENGER ");
        txaInp = new JTextArea();
        send = new JButton("SEND");
        openFileTxt = new JButton("OPEN FILE .TXT");
        openFileImage = new JButton("OPEN IMAGE");
        inpPanel.add(Messenger);
        inpPanel.add(txaInp);
        inpPanel.add(send);
        inpPanel.add(openFileTxt);

        // Thực hiện sự kiện nút send
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                test = true;
                String mess = txaInp.getText();
                if (mess.equals("")) {
                    System.out.println("Bạn muốn nhắn điều gì???");
                } else {
                    thcl1.senMessage(mess);
                    if (mess != null) {
                        txaInp.setText(null);
                    }
                }
            }
        });
        //        Thực hiện nút open
        openFileTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test = false;
                File newF = new File("D:\\b47.txt");
                thcl1.senFile(newF);
            }
        });
        // Khung chính
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(outpPanel);
        mainPanel.add(inpPanel);
        this.add(addressPanel, BorderLayout.NORTH);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    public void appearTinNhan(String tn) {
        txaOup.append(tn);
    }

    public void appearFile(File file) {
        txaOup1.append(file.toString());
    }

    public static void main(String args[]) {
        new client_Frame("Name_1");
    }
}
