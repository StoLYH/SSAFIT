package com.ssafy.mvc.model.dto;

public class UserRole {
    private int userRole;
    private String userRoleName;

    public UserRole() {}

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public UserRole(int userRole, String userRoleName) {
        this.userRole = userRole;
        this.userRoleName = userRoleName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }
}
