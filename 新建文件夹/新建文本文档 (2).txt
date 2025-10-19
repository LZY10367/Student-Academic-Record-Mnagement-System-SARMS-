import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentAcademicRecordManagementSystem extends JFrame {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "yourpassword";

    // UI Components
    private JTextField tfId, tfName, tfAge, tfMajor;
    private JButton btnAdd, btnUpdate, btnDelete, btnLoad;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentAcademicRecordManagementSystem() {
        setTitle("Student Academic Record Management System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for input fields and buttons
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        tfId = new JTextField();
        tfName = new JTextField();
        tfAge = new JTextField();
        tfMajor = new JTextField();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(tfId);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(tfName);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(tfAge);
        inputPanel.add(new JLabel("Major:"));
        inputPanel.add(tfMajor);

        JPanel btnPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnLoad = new JButton("Load All");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnLoad);

        panel.add(inputPanel);
        panel.add(btnPanel);

        // Table to display students
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Major"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        add(panel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnLoad.addActionListener(e -> loadStudents());

        // Table row click action
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                tfId.setText(tableModel.getValueAt(row, 0).toString());
                tfName.setText(tableModel.getValueAt(row, 1).toString());
                tfAge.setText(tableModel.getValueAt(row, 2).toString());
                tfMajor.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        // Initialize database (create table if not exists)
        initializeDB();
    }

    private void initializeDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "age INT," +
                    "major VARCHAR(100))";
            stmt.execute(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Initialization Error: " + ex.getMessage());
        }
    }

    private void addStudent() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO students (id, name, age, major) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfId.getText()));
            ps.setString(2, tfName.getText());
            ps.setInt(3, Integer.parseInt(tfAge.getText()));
            ps.setString(4, tfMajor.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added!");
            loadStudents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add Error: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "UPDATE students SET name=?, age=?, major=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tfName.getText());
            ps.setInt(2, Integer.parseInt(tfAge.getText()));
            ps.setString(3, tfMajor.getText());
            ps.setInt(4, Integer.parseInt(tfId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student updated!");
            loadStudents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(tfId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student deleted!");
            loadStudents();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete Error: " + ex.getMessage());
        }
    }

    private void loadStudents() {
        tableModel.setRowCount(0); // clear table
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("major")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Load Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            new StudentRecordManagementSystem().setVisible(true);
        });
    }
}