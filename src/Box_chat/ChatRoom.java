package Box_chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class ChatRoom extends JFrame implements ActionListener {

    private JFileChooser chooser;
    private FileSystemView fsv = FileSystemView.getFileSystemView();
    public static ChatRoom chatRoom;
    private JPanel contentPane;
    private PlaceholderTextField tfMessage;
    private String username;
    private JButton btnToggle;
    private JButton btnPicture;
    private JButton btnSend;
    private JTextField tfIP;
    private JTextField tfPort;
    private JTextArea taChat;
    private Socket socket;
    private ClientThread ct;
    private JLabel label;

    /**
     * Create the frame.
     *
     * @throws IOException
     * @throws UnknownHostException
     */
    public ChatRoom(String username) {
        this.username = username;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.NORTH);

        JLabel lblIP = new JLabel("IP Address:");
        panelTop.add(lblIP);

        tfIP = new JTextField();
        tfIP.setColumns(20);
        tfIP.setText("localhost");
//		tfIP.setEnabled(false);
        panelTop.add(tfIP);

        JLabel lblPort = new JLabel("Port:");
        panelTop.add(lblPort);

        tfPort = new JTextField("5500");
        tfPort.setColumns(7);
        panelTop.add(tfPort);

        btnToggle = new JButton("Connect");
        btnToggle.addActionListener(this);
        panelTop.add(btnToggle);

        JPanel panelBottom = new JPanel();
        contentPane.add(panelBottom, BorderLayout.SOUTH);

        tfMessage = new PlaceholderTextField();
        tfMessage.setPlaceholder("Type here!");
        panelBottom.add(tfMessage);
        tfMessage.setColumns(35);
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    send();
                }
            }
        });
        tfMessage.setToolTipText("Message Go Hereeee!");

        btnSend = new JButton();
//        btnSend.setIcon(setIconButton("send"));
        btnSend.setToolTipText("Send Message!");
        btnSend.setEnabled(false);
        btnSend.addActionListener(this);
        panelBottom.add(btnSend);

        btnPicture = new JButton();
//        btnPicture.setIcon(setIconButton("picture"));
        btnPicture.setToolTipText("Send A Picture");
        btnPicture.setEnabled(false);
        btnPicture.addActionListener(this);
        panelBottom.add(btnPicture);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        taChat = new JTextArea();
        taChat.setEditable(false);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                // TODO Auto-generated method stub
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        scrollPane.setViewportView(taChat);

        chooser = new JFileChooser(fsv.getHomeDirectory());
    }

    private void send() {
        if (!tfMessage.getText().toString().trim().isEmpty()) {
            String mess = tfMessage.getText().trim();
            mess = tfMessage.getText().trim();
            ct.sendMessage(mess);
        }
        tfMessage.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand()=="Exit") {
            try {
                ct.sendMessage("\""+username+"\" left the chat!");
                ct.din.close();
                ct.dout.close();
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equalsIgnoreCase("Connect")) {
            int port = 0;
            String address = tfIP.getText().toString();
            try {
                port = Integer.parseInt(tfPort.getText());
                socket = new Socket(address, port);
                ct = new ClientThread(socket, username, this);
                ct.start();
                btnToggle.setText("Exit");
                btnSend.setEnabled(true);
                btnPicture.setEnabled(true);
                tfIP.setEnabled(false);
                tfPort.setEnabled(false);
            } catch (NumberFormatException e2) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(this, "Port must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(this, "Server is offline!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UnknownHostException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        if (e.getSource() == btnSend) {
            send();
        }
    }

    public void appearMessage(String msg) {
        taChat.append(msg);
    }
}
