package Box_chat;

import javax.swing.*;
import java.sql.Connection;

public class Main {

    public static Connection connection = null;

    public static void main(String[] args) {
        try {
            ChatRoom chatRoom = new ChatRoom("Duong loz");
            chatRoom.setVisible(true);
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Cannot connect to the database. Please turn on xampp!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
