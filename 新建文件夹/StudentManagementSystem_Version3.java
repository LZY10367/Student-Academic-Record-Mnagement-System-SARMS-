import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentManagementSystem {
    private Connection connection;
    // 加载 JDBC 驱动



    public StudentManagementSystem() {
        // 加载 JDBC 驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        // 初始化数据库连接
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 创建 GUI
        createGUI();
    }


    private void createGUI() {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        JMenuItem viewStudentItem = new JMenuItem("View Student");
        JMenuItem manageGraduationItem = new JMenuItem("Manage Graduation");
        JMenuItem maintenanceItem = new JMenuItem("System Maintenance");

        menu.add(addStudentItem);
        menu.add(viewStudentItem);
        menu.add(manageGraduationItem);
        menu.add(maintenanceItem);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);

        // Action Listeners
        addStudentItem.addActionListener(e -> addStudent());
        viewStudentItem.addActionListener(e -> viewStudent());
        manageGraduationItem.addActionListener(e -> manageGraduation());
        maintenanceItem.addActionListener(e -> systemMaintenance());

        frame.setVisible(true);
    }

    private void addStudent() {
        JFrame addFrame = new JFrame("Add Student");
        addFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField semesterField = new JTextField();
        JTextField gradeField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Semester:"));
        panel.add(semesterField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                String sql = "INSERT INTO student (name, semester, grade) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, semesterField.getText());
                preparedStatement.setString(3, gradeField.getText());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Student added successfully!");
                addFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error adding student: " + ex.getMessage());
            }
        });

        panel.add(submitButton);

        addFrame.add(panel);
        addFrame.setVisible(true);
    }

    private void viewStudent() {
        JFrame viewFrame = new JFrame("View Student");
        viewFrame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            String sql = "SELECT * FROM student";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                textArea.append("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Semester: " + resultSet.getString("semester") +
                        ", Grade: " + resultSet.getString("grade") + "\n");
            }
        } catch (SQLException ex) {
            textArea.append("Error fetching student: " + ex.getMessage());
        }

        viewFrame.add(scrollPane);
        viewFrame.setVisible(true);
    }

    private void manageGraduation() {
        JFrame gradFrame = new JFrame("Manage Graduation");
        gradFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JTextField idField = new JTextField();
        JTextField statusField = new JTextField();

        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Graduation Status (Outstanding, Pass, Fail):"));
        panel.add(statusField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                String sql = "UPDATE student SET graduation_status = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, statusField.getText());
                preparedStatement.setInt(2, Integer.parseInt(idField.getText()));
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Graduation status updated successfully!");
                gradFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error updating graduation status: " + ex.getMessage());
            }
        });

        panel.add(updateButton);

        gradFrame.add(panel);
        gradFrame.setVisible(true);
    }

    private void systemMaintenance() {
        JOptionPane.showMessageDialog(null, "System maintenance is not implemented yet.");
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}