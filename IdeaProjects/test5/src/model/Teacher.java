package model;

public class Teacher extends User {
    private String department;

    public Teacher(int id, String username, String password, String department) {
        super(id, username, password, "teacher");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}