package com.ssafy.mvc.model.dto;

public class UserDetail {
    private String userId;
    private String onelineInfo;
    private String exper;

    // 기본 생성자
    public UserDetail() {}

    // 모든 필드를 포함한 생성자
    public UserDetail(String userId, String onelineInfo, String exper) {
        this.userId = userId;
        this.onelineInfo = onelineInfo;
        this.exper = exper;
    }

    // Getter and Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOnelineInfo() {
        return onelineInfo;
    }

    public void setOnelineInfo(String onelineInfo) {
        this.onelineInfo = onelineInfo;
    }

    public String getExper() {
        return exper;
    }

    public void setExper(String exper) {
        this.exper = exper;
    }

    @Override
    public String toString() {
        return "UserDetail [userId=" + userId + ", onelineInfo=" + onelineInfo + ", exper=" + exper + "]";
    }
} 