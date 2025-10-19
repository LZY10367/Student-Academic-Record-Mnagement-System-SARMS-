import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

// 学期成绩类
class Grade {
    private String studentId;
    private int semester;
    private double score;

    public Grade(String studentId, int semester, double score) {
        this.studentId = studentId;
        this.semester = semester;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getSemester() {
        return semester;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String toString() {
        return "学号: " + studentId + " 学期: " + semester + " 成绩: " + score;
    }
}

// 学籍管理方案类
class AcademicStatus {
    public static String getStatus(double totalScore) {
        if (totalScore >= 85) {
            return "优秀";
        } else if (totalScore >= 60) {
            return "合格";
        } else if (totalScore >= 50) {
            return "跟读";
        } else {
            return "退学";
        }
    }
}

// 学生类
class Student {
    private String studentId;
    private String name;
    private String gender;
    private String major;
    private String enrollmentDate;
    private String academicStatus;
    private List<Grade> grades;
    private double totalScore;  // 用于存储手动输入的总成绩

    public Student(String studentId, String name, String gender, String major, String enrollmentDate, String academicStatus) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.enrollmentDate = enrollmentDate;
        this.academicStatus = academicStatus;
        this.grades = new ArrayList<>();
        this.totalScore = 0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    // 添加学期成绩
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    // 设置总成绩（手动输入）
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    // 获取学籍管理结果
    public String getAcademicResult() {
        return AcademicStatus.getStatus(this.totalScore);
    }

    // 返回所有学期成绩
    public List<Grade> getGrades() {
        return grades;
    }

    public String toString() {
        return studentId + "  " + name + "  " + gender + "  " + major + "  " + enrollmentDate + "  " + academicStatus;
    }
}

// 学生管理类
class SystemMaintenance {
    private List<Student> students;

    public SystemMaintenance() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }
}

public class MainUI {
    private JFrame frame;
    private SystemMaintenance systemMaintenance;
    private JTextField studentIdField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField majorField;
    private JTextField enrollmentDateField;
    private JTextField academicStatusField;
    private JTextField semesterField;
    private JTextField scoreField;
    private JTextField totalScoreField;  // 用于输入总成绩
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public MainUI() {
        systemMaintenance = new SystemMaintenance();

        // 初始化窗口
        frame = new JFrame("学籍管理系统");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 创建顶部面板用于输入字段
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout()); // 使用GridBagLayout来灵活布局
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel studentIdLabel = new JLabel("学号:");
        studentIdField = new JTextField(15);
        JLabel nameLabel = new JLabel("姓名:");
        nameField = new JTextField(15);
        JLabel genderLabel = new JLabel("性别:");
        genderField = new JTextField(15);
        JLabel majorLabel = new JLabel("专业:");
        majorField = new JTextField(15);
        JLabel enrollmentDateLabel = new JLabel("入学时间:");
        enrollmentDateField = new JTextField(15);
        JLabel academicStatusLabel = new JLabel("期末评估:");
        academicStatusField = new JTextField(15);

        JLabel semesterLabel = new JLabel("学期:");
        semesterField = new JTextField(5);
        JLabel scoreLabel = new JLabel("成绩:");
        scoreField = new JTextField(5);

        JLabel totalScoreLabel = new JLabel("总成绩:");  // 新增总成绩输入框
        totalScoreField = new JTextField(5);  // 用于输入总成绩

        // 设置布局位置
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(studentIdLabel, gbc);
        gbc.gridx = 1; inputPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(genderLabel, gbc);
        gbc.gridx = 1; inputPanel.add(genderField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(majorLabel, gbc);
        gbc.gridx = 1; inputPanel.add(majorField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(enrollmentDateLabel, gbc);
        gbc.gridx = 1; inputPanel.add(enrollmentDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(academicStatusLabel, gbc);
        gbc.gridx = 1; inputPanel.add(academicStatusField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        inputPanel.add(semesterLabel, gbc);
        gbc.gridx = 1; inputPanel.add(semesterField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        inputPanel.add(scoreLabel, gbc);
        gbc.gridx = 1; inputPanel.add(scoreField, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        inputPanel.add(totalScoreLabel, gbc);  // 设置总成绩输入框标签
        gbc.gridx = 1; inputPanel.add(totalScoreField, gbc);  // 设置总成绩输入框

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton btnAddStudent = new JButton("添加学生");
        JButton btnAddGrade = new JButton("添加成绩");
        JButton btnQueryStudent = new JButton("查询学生");
        JButton btnClear = new JButton("清空");

        btnAddStudent.addActionListener(e -> addStudent());
        btnAddGrade.addActionListener(e -> addGrade());
        btnQueryStudent.addActionListener(e -> queryStudent());
        btnClear.addActionListener(e -> clearFields());

        buttonPanel.add(btnAddStudent);
        buttonPanel.add(btnAddGrade);
        buttonPanel.add(btnQueryStudent);
        buttonPanel.add(btnClear);

        // 创建表格用于显示学生信息
        String[] columns = {"序号", "学号", "姓名", "性别", "专业", "入学时间", "期末评估", "学期", "学期成绩", "总成绩", "学籍管理"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // 将组件添加到主面板
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // 设置窗口
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // 添加学生方法
    private void addStudent() {
        String studentId = studentIdField.getText();
        String name = nameField.getText();
        String gender = genderField.getText();
        String major = majorField.getText();
        String enrollmentDate = enrollmentDateField.getText();
        String academicStatus = academicStatusField.getText();

        // 创建一个新的学生并添加到系统中
        Student student = new Student(studentId, name, gender, major, enrollmentDate, academicStatus);
        systemMaintenance.addStudent(student);

        // 更新表格显示学生数据
        tableModel.addRow(new Object[]{
                tableModel.getRowCount() + 1, studentId, student.getName(), student.getGender(), student.getMajor(), student.getEnrollmentDate(), student.getAcademicStatus()
        });
    }

    // 添加成绩方法
    private void addGrade() {
        String studentId = studentIdField.getText();
        int semester = Integer.parseInt(semesterField.getText()); // 获取学期
        double score = Double.parseDouble(scoreField.getText());  // 获取成绩
        double totalScore = Double.parseDouble(totalScoreField.getText()); // 获取手动输入的总成绩

        // 查找学生并添加成绩
        Student student = systemMaintenance.findStudent(studentId);
        if (student != null) {
            Grade grade = new Grade(studentId, semester, score);
            student.addGrade(grade);
            student.setTotalScore(totalScore); // 设置总成绩

            // 更新表格显示学生数据
            tableModel.addRow(new Object[]{
                    tableModel.getRowCount() + 1, studentId, student.getName(), student.getGender(), student.getMajor(), student.getEnrollmentDate(),
                    student.getAcademicStatus(), semester, score, totalScore, student.getAcademicResult()
            });

            // 强制刷新表格
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(frame, "学生未找到");
        }
    }

    // 查询学生信息方法
    private void queryStudent() {
        String studentId = studentIdField.getText();
        Student student = systemMaintenance.findStudent(studentId);
        if (student != null) {
            JOptionPane.showMessageDialog(frame, student.toString());
        } else {
            JOptionPane.showMessageDialog(frame, "学生未找到");
        }
    }

    // 清空输入框方法
    private void clearFields() {
        studentIdField.setText("");
        nameField.setText("");
        genderField.setText("");
        majorField.setText("");
        enrollmentDateField.setText("");
        academicStatusField.setText("");
        semesterField.setText("");
        scoreField.setText("");
        totalScoreField.setText(""); // 清空总成绩
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
