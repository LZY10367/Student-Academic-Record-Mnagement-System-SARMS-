import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentRecordManagementSystem extends JFrame {
    private ArrayList<Student> students;
    private DefaultTableModel tableModel;
    private JTable studentTable;

    public StudentRecordManagementSystem() {
        students = new ArrayList<>();
        setTitle("Student Academic Record Management System");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table and model
        String[] columns = {"ID", "Name", "Major", "GPA"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField majorField = new JTextField();
        JTextField gpaField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Major:"));
        formPanel.add(majorField);
        formPanel.add(new JLabel("GPA:"));
        formPanel.add(gpaField);

        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton deleteButton = new JButton("Delete Selected");
        JButton updateButton = new JButton("Update Selected");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String major = majorField.getText().trim();
            String gpaStr = gpaField.getText().trim();
            if (id.isEmpty() || name.isEmpty() || major.isEmpty() || gpaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }
            try {
                double gpa = Double.parseDouble(gpaStr);
                if (gpa < 0.0 || gpa > 4.0) throw new NumberFormatException();
                Student s = new Student(id, name, major, gpa);
                students.add(s);
                tableModel.addRow(new Object[]{id, name, major, gpa});
                idField.setText(""); nameField.setText(""); majorField.setText(""); gpaField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid GPA! (0.0 - 4.0)");
            }
        });

        deleteButton.addActionListener(e -> {
            int selected = studentTable.getSelectedRow();
            if (selected >= 0) {
                students.remove(selected);
                tableModel.removeRow(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Select a student to delete!");
            }
        });

        updateButton.addActionListener(e -> {
            int selected = studentTable.getSelectedRow();
            if (selected >= 0) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String major = majorField.getText().trim();
                String gpaStr = gpaField.getText().trim();
                try {
                    double gpa = Double.parseDouble(gpaStr);
                    if (gpa < 0.0 || gpa > 4.0) throw new NumberFormatException();
                    Student s = new Student(id, name, major, gpa);
                    students.set(selected, s);
                    tableModel.setValueAt(id, selected, 0);
                    tableModel.setValueAt(name, selected, 1);
                    tableModel.setValueAt(major, selected, 2);
                    tableModel.setValueAt(gpa, selected, 3);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid GPA! (0.0 - 4.0)");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a student to update!");
            }
        });

        // Table row selection
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int selected = studentTable.getSelectedRow();
            if (selected >= 0) {
                idField.setText(tableModel.getValueAt(selected, 0).toString());
                nameField.setText(tableModel.getValueAt(selected, 1).toString());
                majorField.setText(tableModel.getValueAt(selected, 2).toString());
                gpaField.setText(tableModel.getValueAt(selected, 3).toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRecordManagementSystem().setVisible(true));
    }
}

class Student {
    private String id;
    private String name;
    private String major;
    private double gpa;

    public Student(String id, String name, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
    }
    // getters and setters can be added if needed
}