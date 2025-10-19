package ui;

import model.Manager;
import javax.swing.*;
import java.awt.*;

public class ManagerFrame extends JFrame {
    private Manager manager;

    public ManagerFrame(Manager manager) {
        this.manager = manager;
        setTitle("Manager Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel userManagementPanel = new JPanel();
        userManagementPanel.add(new JLabel("User Management - Add/Delete Teacher/Student, Authority Distribution"));

        JPanel systemPanel = new JPanel();
        JButton backupBtn = new JButton("Backup Database");
        JButton restoreBtn = new JButton("Restore Database");
        systemPanel.add(backupBtn);
        systemPanel.add(restoreBtn);

        JPanel monitorPanel = new JPanel();
        monitorPanel.add(new JLabel("Database Operation Monitoring"));

        tabbedPane.add("User Management", userManagementPanel);
        tabbedPane.add("System Maintenance", systemPanel);
        tabbedPane.add("Monitoring", monitorPanel);

        add(tabbedPane);
    }
}