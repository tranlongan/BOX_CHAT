package Box_chat_remake2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    JPanel main, main1, mainImg, mainPanel, mainPanel1, panelTk, panelMk, panelXnMk, panelBtn, panelHdnBtn, panelEr;
    JLabel lbTk, lbMk, lbXnMK, error, lbImg,lbTitleImg;
    JTextField tfTk;
    JPasswordField tfMk, tfXnMk;
    JButton btnDn, btnDn_Hiden, btnDk, btnDk_Hiden;
    ImageIcon img;
    String getName;

    Connection conn;
    Statement sm;
    ResultSet rs;
    ResultSetMetaData rsm;


    public Login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_account_login?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            sm = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("LOGIN");
        setLocation(575,80);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 460);
        panelTk = new JPanel();
        panelTk.setLayout(new BoxLayout(panelTk, BoxLayout.X_AXIS));
        panelTk.setBorder(BorderFactory.createEmptyBorder(60, 10, 5, 10));
        lbTk = new JLabel("Tài khoản");
        lbTk.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 56));
        tfTk = new JTextField();
        panelTk.add(lbTk);
        panelTk.add(tfTk);

        panelMk = new JPanel();
        panelMk.setLayout(new BoxLayout(panelMk, BoxLayout.X_AXIS));
        panelMk.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lbMk = new JLabel("Mật khẩu");
        lbMk.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        tfMk = new JPasswordField();
        panelMk.add(lbMk);
        panelMk.add(tfMk);

        panelBtn = new JPanel();
        panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));
//        panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        btnDn = new JButton("Đăng nhập");
        btnDk = new JButton("Đăng ký");

        panelEr = new JPanel();
        panelEr.setLayout(new BoxLayout(panelEr, BoxLayout.X_AXIS));
        error = new JLabel("");
        error.setVisible(false);
        panelEr.add(error);
        panelBtn.add(btnDn);
        panelBtn.add(btnDk);

        panelHdnBtn = new JPanel();
        panelHdnBtn.setLayout(new BoxLayout(panelHdnBtn, BoxLayout.X_AXIS));
        panelHdnBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        btnDn_Hiden = new JButton("ĐĂNG NHẬP");
        btnDn_Hiden.setVisible(false);
        btnDk_Hiden = new JButton("ĐĂNG KÝ");
        btnDk_Hiden.setVisible(false);
        panelHdnBtn.add(btnDn_Hiden);
        panelHdnBtn.add(btnDk_Hiden);

        panelXnMk = new JPanel();
        panelXnMk.setLayout(new BoxLayout(panelXnMk, BoxLayout.X_AXIS));
        panelXnMk.setBorder(BorderFactory.createEmptyBorder(5, 10, 50, 10));
        lbXnMK = new JLabel("Xác nhận mật khẩu");
        lbXnMK.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        tfXnMk = new JPasswordField();
        panelXnMk.add(lbXnMK);
        panelXnMk.add(tfXnMk);
        panelXnMk.setVisible(false);

        btnDn_Hiden.addActionListener((ActionListener) e -> {
            try {
                char[] mk = tfMk.getPassword();
                String a = new String(mk);
                String tk = tfTk.getText().trim();
                String i = "";
                if (tk.equals("") || a.trim().equals("")) {
                    error.setText("Bạn không được để trống bất kỳ mục nào");
                    error.setForeground(Color.red);
                    error.setVisible(true);
                } else {
                    try {
                        String sql = "";
                        String tk_rs = "", mk_rs = "";
                        sql = "SELECT tai_khoan, mat_khau FROM account_login";
                        rs = sm.executeQuery(sql);
                        while (rs.next()) {
                            tk_rs = rs.getString("tai_khoan");
                            mk_rs = rs.getString("mat_khau");
                            if (tk.equals(tk_rs) && a.equals(mk_rs)) {
                                getName = tfTk.getText();
                                new client_Interface(getName);
                                dispose();
                            } else {
                                error.setText("Tài khoản hoặc mật khẩu không chính xác!");
                                error.setForeground(Color.red);
                                error.setVisible(true);
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (NullPointerException e1) {
                System.out.print("Bạn không được để trống bất kỳ mục nào");
            }
        });
        btnDk_Hiden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    char[] mk = tfMk.getPassword();
                    String a = new String(mk);
                    char[] mkHdn = tfXnMk.getPassword();
                    String b = new String(mkHdn);
                    String tk = tfTk.getText().trim();
                    String i = "";
                    if (tk.equals("") || a.trim().equals("") || b.trim().equals("")) {
                        error.setText("Bạn không được để trống bất kỳ mục nào");
                        error.setForeground(Color.red);
                        error.setVisible(true);
                    } else {
                        try {
                            if (b.trim().equals(a.trim())) {
                                String sql = "";
                                sql = "insert into account_login(id,tai_khoan,mat_khau) values(?, ?, ?)";
                                PreparedStatement ptmt = conn.prepareStatement(sql);
                                ptmt.setString(1, null);
                                ptmt.setString(2, tk);
                                ptmt.setString(3, a);
                                ptmt.execute();
                                error.setText("Đăng ký thành công");
                                error.setForeground(Color.GREEN);
                                error.setVisible(true);
                            } else {
                                error.setText("Hai phần mật khẩu không giống nhau");
                                error.setForeground(Color.RED);
                                error.setVisible(true);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (NullPointerException e1) {
                    System.out.print("Bạn không được để trống bất kỳ mục nào");
                }
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(panelTk);
        mainPanel.add(panelMk);
        mainPanel.add(panelXnMk);
        mainPanel.add(panelEr);

        mainPanel1 = new JPanel();
        mainPanel1.setLayout(new BoxLayout(mainPanel1,BoxLayout.X_AXIS));
        mainPanel1.add(panelBtn);
        mainPanel1.setBorder(BorderFactory.createEmptyBorder(8,0,0,374));
        mainPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.LIGHT_GRAY));
        mainPanel.setBackground(Color.WHITE);

        mainImg = new JPanel();
        img = new ImageIcon("img/images.png");
        Image dabImage = img.getImage();
        Image setImg = dabImage.getScaledInstance(225,125, Image.SCALE_SMOOTH);
        img = new ImageIcon(setImg);
        lbImg = new JLabel(img);
        lbTitleImg = new JLabel("TCP/ LOGIN CLIENT ");
        mainImg.add(lbImg);
        mainImg.add(lbTitleImg);
        mainImg.setBorder(BorderFactory.createEmptyBorder(0,-194,0,0));
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
//        main.add(mainImg);
        main.add(mainPanel);
        panelHdnBtn.setBackground(Color.white);
        panelTk.setBackground(Color.white);
        panelMk.setBackground(Color.white);
        panelXnMk.setBackground(Color.white);
        mainPanel.add(panelHdnBtn);
        main.setVisible(false);


        main1 = new JPanel();
        main1.setLayout(new BoxLayout(main1, BoxLayout.Y_AXIS));
        main1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        btnDn_Hiden.setVisible(true);
        btnDn.setBorderPainted(false);
        btnDn.setBackground(Color.CYAN);
        btnDk.setBackground(Color.LIGHT_GRAY);
        main1.add(mainImg);
        main1.add(mainPanel1);
        main1.add(mainPanel);

        this.add(main);
        this.add(main1);

        btnDn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDn.setBorderPainted(false);
                btnDn.setBackground(Color.CYAN);
                btnDk.setBorderPainted(true);
                btnDk.setBackground(Color.LIGHT_GRAY);
                btnDn_Hiden.setVisible(true);
                btnDk_Hiden.setVisible(false);
                panelXnMk.setVisible(false);
                main.setVisible(true);
                mainPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.LIGHT_GRAY));
                main.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
                tfTk.setText(null);
                tfMk.setText(null);
            }
        });

        btnDk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDn.setBorderPainted(false);
                btnDn.setBackground(Color.LIGHT_GRAY);
                btnDk.setBorderPainted(true);
                btnDk.setBackground(Color.CYAN);
                btnDk_Hiden.setVisible(true);
                btnDn_Hiden.setVisible(false);
                panelXnMk.setVisible(true);
                main.setVisible(true);
                mainPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.LIGHT_GRAY));
                main.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            }
        });
        this.setVisible(true);
    }
}
