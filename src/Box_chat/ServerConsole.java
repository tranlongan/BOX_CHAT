package Box_chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class ServerConsole extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField tfCommand;
    private JTextField tfIP;
    private JTextField tfPort;
    private static JTextArea taConsole;
    private JButton btn;

    /**
     * Create the frame.
     */
    public ServerConsole(int port) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        taConsole = new JTextArea();
        taConsole.setFont(new Font("Ubuntu Condensed", Font.BOLD, 15));
        taConsole.setBackground(Color.BLACK);
        taConsole.setForeground(Color.WHITE);
        taConsole.setEditable(false);
        scrollPane.setViewportView(taConsole);

        JPanel panelBottom = new JPanel();
        contentPane.add(panelBottom, BorderLayout.SOUTH);

        tfCommand = new JTextField();
        tfCommand.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    command();
                }
            }
        });
        panelBottom.add(tfCommand);
        tfCommand.setColumns(30);

        btn = new JButton(">>");
        btn.addActionListener(this);
        panelBottom.add(btn);

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.NORTH);

        JLabel lblIp = new JLabel("IP: ");
        panelTop.add(lblIp);

        tfIP = new JTextField("127.0.0.1");
        tfIP.setEditable(false);
        panelTop.add(tfIP);
        tfIP.setColumns(10);

        JLabel lblPort = new JLabel("Port: ");
        panelTop.add(lblPort);

        tfPort = new JTextField(String.valueOf(port));
        panelTop.add(tfPort);
        tfPort.setEditable(false);
        tfPort.setColumns(5);
    }

    public static void appendMessage(String message) {
        taConsole.append(message);
    }

    private void command() {
        String sql = tfCommand.getText().toString();
        if(sql.equalsIgnoreCase("exit")) {
            if(!Server.connections.isEmpty()) {
                ServerThread.sendMessageToAllClient("Server is shutting down...\n");
                try {
                    Server.ss.close();
                    ServerThread.sendMessageToAllClient("Server is closed!\nClient will close soon!");
                    Thread.sleep(3500);
                    System.exit(1);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
                appendMessage("Server is exiting!");
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.exit(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        } else if (sql.equalsIgnoreCase("list username")) {
            try {
                Statement stmt = Server.connection.createStatement();
                ResultSet rs = stmt.executeQuery("select Username from Accounts");
                while (rs.next()) {
                    ServerConsole.appendMessage(rs.getString(1)+"\n");
                }
                ServerConsole.appendMessage("---All Usernames have been displayed!---\n");
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (sql.equalsIgnoreCase("list chatname")) {
            try {
                Statement stmt = Server.connection.createStatement();
                ResultSet rs = stmt.executeQuery("select Name from Accounts");
                while (rs.next()) {
                    ServerConsole.appendMessage(rs.getString(1)+"\n");
                }
                ServerConsole.appendMessage("---All Chat Names have been displayed!---\n");
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            ServerConsole.appendMessage(sql+"\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        command();
    }
}

