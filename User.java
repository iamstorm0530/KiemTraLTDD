package com.example.midtest;

public class User {
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String avatar;
    public User(String id, String name, String username, String password, String email, String avatar) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getAvatar() { return avatar; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
