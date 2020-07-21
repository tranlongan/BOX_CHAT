package Clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.Socket;

public class TimeClient2 extends JFrame{
    TimeClientThread2 clientThread2;
    Socket socket;
    DataInputStream din ;

    JPanel mainPanel,outPanel,addressPanel;
    static  JTextArea txaOut;
    JLabel lbIp, lbPort;
    JTextField tfPort, tfIp;
    JButton btConnect;
    JScrollPane jScrollPane;

    public TimeClient2() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Clock_2");

        addressPanel = new JPanel();
        lbIp = new JLabel("IP");
        tfIp = new JTextField("localhost");
        tfIp.setColumns(8);
        tfIp.setEditable(false);
        lbPort = new JLabel("PORT");
        tfPort = new JTextField("7000");
        tfPort.setColumns(5);
        btConnect = new JButton("CONNECT");
        btConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int port;
                    String address = tfIp.getText().toString();
                    port = Integer.parseInt(tfPort.getText());

                    socket = new Socket(address, port);
                    clientThread2 = new TimeClientThread2(socket, TimeClient2.this);
                    clientThread2.start();
                } catch (Exception e1) {
                    // TODO: handle exception
                    e1.printStackTrace();
                }
            }
        });
        addressPanel.add(lbIp);
        addressPanel.add(tfIp);
        addressPanel.add(lbPort);
        addressPanel.add(tfPort);
        addressPanel.add(btConnect);

        outPanel = new JPanel();
        outPanel.setLayout(new BoxLayout(outPanel,BoxLayout.X_AXIS));
        outPanel.setPreferredSize(new Dimension(400,80));
        outPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10) );
        txaOut = new JTextArea();
        txaOut.setEditable(false);
        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(txaOut);
        outPanel.add(jScrollPane);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(outPanel);

        this.add(addressPanel, BorderLayout.NORTH);
        this.add(outPanel);
        this.add(outPanel);
        this.pack();
        this.setVisible(true);
    }
    public static void main(String args[]) {
        new TimeClient2();
    }
    public static void appendTime(String time){
        txaOut.append(time);
    }
}
