package ui;

import database.DBConnection;
import model.Manager;
import model.Student;
import model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Student Academic Record Management System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username: "));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(new JLabel(""));
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                int id = rs.getInt("id");
                switch (role) {
                    case "manager":
                        new ManagerFrame(new Manager(id, username, password)).setVisible(true);
                        break;
                    case "teacher":
                        new TeacherFrame(new Teacher(id, username, password, rs.getString("department"))).setVisible(true);
                        break;
                    case "student":
                        new StudentFrame(new Student(id, username, password, rs.getString("major"))).setVisible(true);
                        break;
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}