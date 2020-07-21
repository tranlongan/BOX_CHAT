package Box_chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JTextField tfUsername;
    private JPasswordField pfPass;

    public Login() {
        setTitle("Login");
//		setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(510, 220, 357, 171);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(42, 47, 86, 14);
        contentPane.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(146, 45, 176, 20);
        contentPane.add(tfUsername);
        tfUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(42, 78, 86, 14);
        contentPane.add(lblPassword);

        pfPass = new JPasswordField();
        pfPass.setBounds(146, 77, 176, 20);
        contentPane.add(pfPass);
        pfPass.setColumns(10);

        JLabel lblLogin = new JLabel("LOGIN");
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblLogin.setBounds(138, 11, 95, 22);
        contentPane.add(lblLogin);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        btnLogin.setBounds(127, 108, 103, 23);
        contentPane.add(btnLogin);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(this);
        btnExit.setBounds(240, 109, 103, 23);
        contentPane.add(btnExit);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(12, 108, 103, 23);
        btnRegister.addActionListener(this);
        contentPane.add(btnRegister);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Login"))
        {
            String pw = String.valueOf(pfPass.getPassword());
            String sql = "select * from Accounts";//câu lệnh sql
            try
            {
                PreparedStatement stmt = Main.connection.prepareStatement(sql);//tạo PreparedStatement
                ResultSet rs = stmt.executeQuery();//tạo ResultSet
                if(tfUsername.getText().equals("")||pw.equals(""))//nếu 2 trường bị bỏ trống
                {
                    JOptionPane.showMessageDialog(this, "Please Input both Username and Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }else
                {
                    if(!rs.first())//kiểm tra nếu không có dòng dữ liệu đầu tiên của ResultSet
                    {
                        JOptionPane.showMessageDialog(this, "Incorrect Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                        rs.close();//đóng ResultSet củ cặc đó lại(có thể không cẩn đóng)
                    }else//nếu không thì làm típ
                    {
                        ResultSet rstemp = stmt.executeQuery();
                        while(rstemp.next())//chạy ResultSet từ dòng dữ liệu đầu đến cuối ResultSet
                        {
                            String usnmtemp = rstemp.getString("Username");//tạo biến String chứa dữ liệu của username hiện thời
                            String pwtemp = rstemp.getString("Password");//tạo biến String chứa dữ liệu của password hiện thời
                            if(usnmtemp.equalsIgnoreCase(tfUsername.getText())&&pwtemp.equalsIgnoreCase(pw))//so sánh nếu dữ liệu trong trường dữ liệu trùng với dữ liệu lấy ra hiện thời
                            {
                                ResultSet rss = stmt.executeQuery("select Name from Accounts where Username = '"+usnmtemp+"'");
                                rss.first();
                                String name = rss.getString(1);
                                ChatRoom chatRoom = new ChatRoom(name);
                                dispose();
                                JOptionPane.showMessageDialog(this, "You are now logon!", "Successfully", JOptionPane.INFORMATION_MESSAGE);
                                chatRoom.setVisible(true);//hiện cửa sổ chính
                                break;
                            }else
                            {
                                if(rstemp.isLast()) //kiểm tra dữ liệu có phải là dòng cuối cùng của ResultSet hay không
                                    JOptionPane.showMessageDialog(this, "Incorrect Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        if(e.getActionCommand().equals("Register"))
        {
            Register regist = new Register();
            regist.setVisible(true);//mở cửa sổ đăng kí tài khoản
        }
        if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
    }
}
