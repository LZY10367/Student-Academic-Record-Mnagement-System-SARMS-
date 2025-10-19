package model;

public class Student extends User {
    private String major;
    // Add more fields as needed

    public Student(int id, String username, String password, String major) {
        super(id, username, password, "student");
        this.major = major;
    }

    public String getMajor() {
        return major;
    }
}