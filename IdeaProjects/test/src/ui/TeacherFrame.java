package ui;

import model.Teacher;
import javax.swing.*;
import java.awt.*;

public class TeacherFrame extends JFrame {
    private Teacher teacher;

    public TeacherFrame(Teacher teacher) {
        this.teacher = teacher;
        setTitle("Teacher Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Student Academic Management
        JPanel academicPanel = new JPanel();
        academicPanel.add(new JLabel("Enter/Modify Student Info, Initiate Early Warning, Graduation Qualification"));

        // Review Applications
        JPanel reviewPanel = new JPanel();
        reviewPanel.add(new JLabel("Review Graduation & Status Change Applications"));

        // Information Registration
        JPanel regPanel = new JPanel();
        regPanel.add(new JLabel("Register Freshmen, Current Students, Graduates, Lost/Reissue Documents"));

        tabbedPane.add("Academic Management", academicPanel);
        tabbedPane.add("Review Applications", reviewPanel);
        tabbedPane.add("Information Registration", regPanel);

        add(tabbedPane);
    }
}