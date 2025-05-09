package com.ssafy.mvc.model.dto;

public class User {
    private String user_id;
    private String user_name;
    private String role;
    private String password;

    //생성자

    public User(String user_id, String user_name, String role, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.role = role;
        this.password = password;
    }

    //getter setter

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
