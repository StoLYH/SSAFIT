package com.ssafy.mvc.model.dto;

public class User {
    private String userId;
    private String userName;
    private String userRole;
    private String password;
    //생성자


    public User(String userId, String userName, String userRole, String password) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userRole=" + userRole + ", password=" + password
				+ "]";
	}
    
}

