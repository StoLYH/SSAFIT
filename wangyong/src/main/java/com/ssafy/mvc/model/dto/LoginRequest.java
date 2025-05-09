package com.ssafy.mvc.model.dto;

public class LoginRequest {
    private String uesrId;
    private String password;

    public LoginRequest(String uesrId, String password) {
        this.uesrId = uesrId;
        this.password = password;
    }

    public String getUesrId() {
        return uesrId;
    }
    public void setUesrId(String uesrId) {
        this.uesrId = uesrId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
