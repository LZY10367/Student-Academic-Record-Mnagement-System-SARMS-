package model;

public class Manager extends User {
    public Manager(int id, String username, String password) {
        super(id, username, password, "manager");
    }
}