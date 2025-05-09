package com.ssafy.mvc.model.dto;

public class LoginRequest {
    private String userId;
    private String password;

    public LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }
    public void setUesrId(String uesrId) {
        this.userId = uesrId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
