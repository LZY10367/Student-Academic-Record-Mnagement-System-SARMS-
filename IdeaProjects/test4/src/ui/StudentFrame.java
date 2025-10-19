package ui;

import model.Student;

import javax.swing.*;

public class StudentFrame extends JFrame {
    private final Student student;

    public StudentFrame(Student student) {
        this.student = student;
        setTitle("Student Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Personal Info Inquiry
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("View/Modify Personal Info, Print Grades"));

        // Academic Applications
        JPanel appPanel = new JPanel();
        appPanel.add(new JLabel("Submit Applications: Transfer Major, Issue Certificate, Suspension/Resumption"));

        tabbedPane.add("Info Inquiry", infoPanel);
        tabbedPane.add("Academic Applications", appPanel);

        add(tabbedPane);
    }

    public Student getStudent() {
        return student;
    }
}