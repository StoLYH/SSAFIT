package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.RefreshToken;

public interface RefreshTokenDao {
    
    // Refresh Token 저장
    void saveRefreshToken(RefreshToken refreshToken);
    
    // Refresh Token 존재 여부 확인
    int findByToken(String token);
    
    // Refresh Token 삭제 (로그아웃 시)
    void deleteByUserId(String userId);
} 