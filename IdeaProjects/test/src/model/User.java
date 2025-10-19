package model;

public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String role; // "manager", "student", "teacher"

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public String getUsername() {
        return username;
    }
    public int getId() {
        return id;
    }
}