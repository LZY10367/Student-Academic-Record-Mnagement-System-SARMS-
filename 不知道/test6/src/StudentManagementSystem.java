
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.classfile.constantpool.LongEntry;
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management",
                    "root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 创建 GUI
        createGUI();
    }


    private void createGUI() {
        JFrame frame = new JFrame("学生学籍管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("操作：");
        JMenuItem addStudentItem = new JMenuItem("添加学生信息->");
        JMenuItem viewStudentItem = new JMenuItem("查看学生信息->");
        JMenuItem manageGraduationItem = new JMenuItem("管理学籍状态->");
//        JMenuItem modifyStudentItem = new JMenuItem("学生信息维护>");
        JMenuItem maintenanceItem = new JMenuItem("系统维护->");


        menu.add(addStudentItem);
        menu.add(viewStudentItem);
        menu.add(manageGraduationItem);
//        menu.add(modifyStudentItem);
        menu.add(maintenanceItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Action Listeners  用于在打开功能窗口时的提醒，非必要
//        addStudentItem.addActionListener(e ->
//                JOptionPane.showMessageDialog(frame, "你点击了【添加学生信息】")
//        );
//
//        viewStudentItem.addActionListener(e ->
//                JOptionPane.showMessageDialog(frame, "你点击了【查看学生信息】")
//        );
//
//        manageGraduationItem.addActionListener(e ->
//                JOptionPane.showMessageDialog(frame, "你点击了【管理学籍状态】")
//        );
//
//        maintenanceItem.addActionListener(e ->
//                JOptionPane.showMessageDialog(frame, "你点击了【系统维护】")
//        );

        // 显示窗口
        frame.setVisible(true);

        addStudentItem.addActionListener(e -> addStudent());
        viewStudentItem.addActionListener(e -> viewStudent());
        manageGraduationItem.addActionListener(e -> manageGraduation());
//        modifyStudentItem.addActionListener(e-> modifyStudentItem());
        maintenanceItem.addActionListener(e -> systemMaintenance());

        frame.setVisible(true);
    }

    private void addStudent() {
        JFrame addFrame = new JFrame("添加学生信息：");
        addFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        JTextField nameField = new JTextField();
        JTextField TotalField = new JTextField();
        JTextField gradeField = new JTextField();
        JTextField idField = new JTextField();
        JTextField graduation_statuesField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField classField = new JTextField();

        panel.add(new JLabel("姓名 :"));
        panel.add(nameField);
        panel.add(new JLabel("总成绩 :"));
        panel.add(TotalField);
        panel.add(new JLabel("年级 :"));
        panel.add(gradeField);
        panel.add(new JLabel("学号 :"));
        panel.add(idField);
        panel.add(new JLabel("学籍状态 :"));
        panel.add(graduation_statuesField);
        panel.add(new JLabel("性别："));
        panel.add(genderField);
        panel.add(new JLabel("班级:"));
        panel.add(classField);

        JButton submitButton = new JButton("确认添加");
        submitButton.addActionListener(e -> {
            try {
                String sql = "INSERT INTO student (name, Total, grade, id, graduation_statues, gender, class) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, TotalField.getText());
                preparedStatement.setString(3, gradeField.getText());
                preparedStatement.setString(4, idField.getText());
                preparedStatement.setString(5, graduation_statuesField.getText());
                preparedStatement.setString(6, genderField.getText());
                preparedStatement.setString(7, classField.getText());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "添加成功!");
                addFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "错误：添加失败 : " + ex.getMessage());
            }
        });

        panel.add(submitButton);

        addFrame.add(panel);
        addFrame.setVisible(true);
    }

    private void viewStudent() {
        JFrame viewFrame = new JFrame("查看学生信息：");
        viewFrame.setSize(600, 400);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(1, 2));
//
//        panel.add(new JLabel("姓名 :"));
//        JTextField nameField = new JTextField();
//        panel.add(nameField);


        try {
//            String sql = "SELECT "+nameField+" FROM student";
            String sql = "SELECT * FROM student";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                textArea.append("\n"+"学号: " + resultSet.getLong("id") + "\n" +"性别： "+ resultSet.getString("gender")+"\n"+"姓名: " + resultSet.getString("name") + "\n" + "总成绩: " + resultSet.getString("Total") + "\n" + "年级: " + resultSet.getString("grade") + "\n" + "学籍状态: " + resultSet.getString("graduation_statues" )+"\n"+"学籍状态: "+resultSet.getString("class"));
            }
        } catch (SQLException ex) {
            textArea.append("Error: " + ex.getMessage());
        }

        viewFrame.add(scrollPane);
        viewFrame.setVisible(true);
    }

    private void manageGraduation() {
        JFrame gradFrame = new JFrame("管理学籍状态");
        gradFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField idField = new JTextField();
        JTextField statuesField = new JTextField();

        panel.add(new JLabel("学号:"));
        panel.add(idField);
        panel.add(new JLabel("新的学籍状态:"));
        panel.add(statuesField);

        JButton updateButton = new JButton("确认更改");
        updateButton.addActionListener(e -> {
            try {
                String sql = "UPDATE student SET graduation_statues = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, statuesField.getText());
                preparedStatement.setLong(2, Long.parseLong(idField.getText()));
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "改动已应用!");
                gradFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "更改失败: " + ex.getMessage());
            }
        });

        panel.add(updateButton);

        gradFrame.add(panel);
        gradFrame.setVisible(true);
    }

//    private void modifyStudentItem(){
//        JFrame gradFrame = new JFrame("管理学籍状态");
//        gradFrame.setSize(400, 300);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(2, 2));
//
//        JTextField idField = new JTextField();
//
//        panel.add(new JLabel("学号:"));
//        panel.add(idField);
//
//        try {
////            String sql = "SELECT "+nameField+" FROM student";
//            String sql = "SELECT * FROM student";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                textArea.append("\n"+"学号: " + resultSet.getLong("id") + "\n" + "姓名: " + resultSet.getString("name") + "\n" + "总成绩: " + resultSet.getString("Total") + "\n" + "年级: " + resultSet.getString("grade") + "\n" + "学籍状态: " + resultSet.getString("graduation_statues" )+"\n");
//            }
//        } catch (SQLException ex) {
//            textArea.append("Error: " + ex.getMessage());
//        }
//
//        JButton updateButton = new JButton("确认更改");
//        updateButton.addActionListener(e -> {
//            try {
//                String sql = "UPDATE student SET graduation_statues = ? WHERE id = ?";
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setString(1, statuesField.getText());
//                preparedStatement.setLong(2, Long.parseLong(idField.getText()));
//                preparedStatement.executeUpdate();
//                JOptionPane.showMessageDialog(null, "改动已应用!");
//                gradFrame.dispose();
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(null, "更改失败: " + ex.getMessage());
//            }
//
//
//
//
//
//
//
//    }
//


    private void systemMaintenance() {
        JOptionPane.showMessageDialog(null, "暂未开放维护！");
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}