package Box_chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("serial")
public class Register extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JTextField tfUsername;
    private JPasswordField pfPass;
    private JPasswordField pfConfirmPass;
    private JTextField tfName;

    /**
     * Create the frame.
     */
    public Register() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(520, 210, 353, 274);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblRegister = new JLabel("REGISTER");
        lblRegister.setBounds(123, 12, 89, 17);
        lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
        contentPane.add(lblRegister);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(23, 42, 73, 14);
        contentPane.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(166, 40, 153, 20);
        contentPane.add(tfUsername);
        tfUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(23, 81, 73, 14);
        contentPane.add(lblPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setBounds(23, 117, 128, 14);
        contentPane.add(lblConfirmPassword);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(this);
        btnRegister.setBounds(81, 200, 94, 23);
        contentPane.add(btnRegister);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(182, 200, 94, 23);
        contentPane.add(btnCancel);

        pfPass = new JPasswordField();
        pfPass.setBounds(166, 79, 153, 20);
        contentPane.add(pfPass);

        pfConfirmPass = new JPasswordField();
        pfConfirmPass.setBounds(166, 115, 153, 20);
        contentPane.add(pfConfirmPass);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(23, 156, 46, 14);
        contentPane.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(166, 154, 153, 20);
        contentPane.add(tfName);
        tfName.setColumns(10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equals("Register"))
        {
            String password  = String.valueOf(pfPass.getPassword());
            String confirmPassword = new String(pfConfirmPass.getPassword());
            String sql = "insert into Accounts (username,password,Name) values ('"+tfUsername.getText()+"','"+password+"','"+tfName.getText()+"')";
            try {
                PreparedStatement stmt = Main.connection.prepareStatement("select * from Accounts");
                ResultSet rs = stmt.executeQuery();
                if(tfUsername.getText().equals("")||password.equals("")||confirmPassword.equals("")||tfName.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Please input all of field!", "Error", JOptionPane.ERROR_MESSAGE);
                }else
                {
                    if(password.equalsIgnoreCase(confirmPassword))
                    {
                        if(!rs.first())
                        {
                            stmt.executeUpdate(sql);
                            dispose();
                            JOptionPane.showMessageDialog(this, "Register Successfully!", "Information",JOptionPane.INFORMATION_MESSAGE);
                        }else
                        {
                            ResultSet rstemp = stmt.executeQuery();
                            while(rstemp.next())
                            {
                                String usnmtemp = rstemp.getString("username");
                                if(usnmtemp.equalsIgnoreCase(tfUsername.getText()))
                                {
                                    JOptionPane.showMessageDialog(this, "Username "+tfUsername.getText()+" already exist!", "Duplicate Username", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                                if(rstemp.isLast())
                                {
                                    stmt.executeUpdate(sql);
                                    dispose();
                                    JOptionPane.showMessageDialog(this, "Register Successfully!", "Information",JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Comfirm Password does not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if(e.getActionCommand().equals("Cancel"))
        {
            this.dispose();
        }
    }
}
