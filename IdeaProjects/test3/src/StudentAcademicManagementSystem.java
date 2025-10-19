import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentAcademicManagementSystem {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/studentacademicrecordmanagementsystem?useSSL=false&serverTimezone=UTC";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Manager", "Student", "Teacher"});

        JButton loginButton = new JButton("Login");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(roleLabel);
        add(roleCombo);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = roleCombo.getSelectedItem().toString();

            if (authenticateUser(username, password, role)) {
                dispose();
                switch (role) {
                    case "Manager" -> new ManagerFrame();
                    case "Student" -> new StudentFrame(username);
                    case "Teacher" -> new TeacherFrame();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private boolean authenticateUser(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(StudentAcademicManagementSystem.DB_URL,
                StudentAcademicManagementSystem.DB_USER, StudentAcademicManagementSystem.DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

class ManagerFrame extends JFrame {
    public ManagerFrame() {
        setTitle("Manager Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton userManagementButton = new JButton("User Management");
        JButton systemMaintenanceButton = new JButton("System Maintenance");
        JButton monitoringButton = new JButton("Monitoring");

        add(userManagementButton);
        add(systemMaintenanceButton);
        add(monitoringButton);

        userManagementButton.addActionListener(e -> new UserManagementFrame());
        systemMaintenanceButton.addActionListener(e -> new SystemMaintenanceFrame());
        monitoringButton.addActionListener(e -> new MonitoringFrame());

        setVisible(true);
    }
}

class UserManagementFrame extends JFrame {
    public UserManagementFrame() {
        setTitle("User Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JButton addUserButton = new JButton("Add User");
        JButton deleteUserButton = new JButton("Delete User");

        add(addUserButton);
        add(deleteUserButton);

        addUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter username:");
            String password = JOptionPane.showInputDialog(this, "Enter password:");
            String role = JOptionPane.showInputDialog(this, "Enter role (Manager/Student/Teacher):");

            try (Connection conn = DriverManager.getConnection(StudentAcademicManagementSystem.DB_URL,
                    StudentAcademicManagementSystem.DB_USER, StudentAcademicManagementSystem.DB_PASSWORD)) {
                String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        deleteUserButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter username to delete:");

            try (Connection conn = DriverManager.getConnection(StudentAcademicManagementSystem.DB_URL,
                    StudentAcademicManagementSystem.DB_USER, StudentAcademicManagementSystem.DB_PASSWORD)) {
                String query = "DELETE FROM users WHERE username=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}

class SystemMaintenanceFrame extends JFrame {
    public SystemMaintenanceFrame() {
        setTitle("System Maintenance");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JButton backupButton = new JButton("Backup Database");
        JButton restoreButton = new JButton("Restore Database");

        add(backupButton);
        add(restoreButton);

        backupButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Backup functionality not implemented yet."));
        restoreButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Restore functionality not implemented yet."));

        setVisible(true);
    }
}

class MonitoringFrame extends JFrame {
    public MonitoringFrame() {
        setTitle("Database Monitoring");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JTextArea logArea = new JTextArea("Monitoring functionality not implemented yet.");
        logArea.setEditable(false);
        add(new JScrollPane(logArea));

        setVisible(true);
    }
}

class StudentFrame extends JFrame {
    public StudentFrame(String username) {
        setTitle("Student Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton inquireButton = new JButton("Inquire Information");
        JButton applicationButton = new JButton("Submit Applications");

        add(inquireButton);
        add(applicationButton);

        inquireButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Inquiry functionality not implemented yet."));
        applicationButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Application functionality not implemented yet."));

        setVisible(true);
    }
}

class TeacherFrame extends JFrame {
    public TeacherFrame() {
        setTitle("Teacher Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton studentManagementButton = new JButton("Student Management");
        JButton reviewApplicationButton = new JButton("Review Applications");
        JButton infoRegistrationButton = new JButton("Information Registration");

        add(studentManagementButton);
        add(reviewApplicationButton);
        add(infoRegistrationButton);

        studentManagementButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Student management functionality not implemented yet."));
        reviewApplicationButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Review application functionality not implemented yet."));
        infoRegistrationButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Information registration functionality not implemented yet."));

        setVisible(true);
    }
}