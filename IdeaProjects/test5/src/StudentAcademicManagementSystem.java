import ui.LoginFrame;

import javax.swing.*;

public static void main(String[] ignoredArgs) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    SwingUtilities.invokeLater(LoginFrame::new);
}