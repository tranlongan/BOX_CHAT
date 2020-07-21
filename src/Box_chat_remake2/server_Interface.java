package Box_chat_remake2;

import javax.swing.*;
import java.awt.*;

public class server_Interface extends JFrame {
    private JLabel lbIp1, lbPort1, lbImg;
    private JPanel mainPanel1, mainPanel2, outPanel1, inpPanel1, addressPanel1;
    private static JTextArea txaOup1;
    private JButton send1;
    private JTextField tfLocalhost1, tfPort1;
    ImageIcon img;
    JScrollPane jScrollPane;

    public server_Interface(int port) {
        this.setLocation(575, 80);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        addressPanel1 = new JPanel();
        lbIp1 = new JLabel("IP");
        tfLocalhost1 = new JTextField("localhost");
        tfLocalhost1.setColumns(10);
        tfLocalhost1.setEditable(false);
        lbPort1 = new JLabel("PORT");
        tfPort1 = new JTextField(String.valueOf(port));
        tfPort1.setColumns(10);
        tfPort1.setEditable(false);
        addressPanel1.add(lbIp1);
        addressPanel1.add(tfLocalhost1);
        addressPanel1.add(lbPort1);
        addressPanel1.add(tfPort1);

        outPanel1 = new JPanel();
        outPanel1.setLayout(new BoxLayout(outPanel1, BoxLayout.X_AXIS));
        outPanel1.setPreferredSize(new Dimension(970, 200));
        outPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txaOup1 = new JTextArea();
        txaOup1.setEditable(false);
        txaOup1.setBackground(Color.black);
        txaOup1.setForeground(Color.white);
        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(txaOup1);
        outPanel1.add(jScrollPane);

        inpPanel1 = new JPanel();
        inpPanel1.setLayout(new BoxLayout(inpPanel1, BoxLayout.X_AXIS));
        inpPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        img = new ImageIcon("img/box_chat.jpg");
        Image dabImage = img.getImage();
        Image setImage = dabImage.getScaledInstance(970,180, Image.SCALE_SMOOTH);
        img = new ImageIcon(setImage);
        mainPanel2 = new JPanel();
        mainPanel2.setLayout(new BoxLayout(mainPanel2,BoxLayout.X_AXIS));
        lbImg = new JLabel(img);
        mainPanel2.add(lbImg);
        mainPanel1 = new JPanel();
        mainPanel1.setLayout(new BoxLayout(mainPanel1, BoxLayout.Y_AXIS));
        mainPanel1.add(mainPanel2);
        mainPanel1.add(outPanel1);
        mainPanel1.add(inpPanel1);



        this.add(addressPanel1, BorderLayout.NORTH);
        this.add(mainPanel1);
        this.pack();
        this.setVisible(true);
    }

    public static void appendMessage(String msg) {
        txaOup1.append(msg);
    }
}
