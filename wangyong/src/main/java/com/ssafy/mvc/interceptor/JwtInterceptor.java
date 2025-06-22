package com.ssafy.mvc.interceptor;

import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dao.RefreshTokenDao;
import com.ssafy.mvc.model.dto.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private JwtUtil jwtUtil;
    private RefreshTokenDao refreshTokenDao;

    public JwtInterceptor(JwtUtil jwtUtil, RefreshTokenDao refreshTokenDao) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenDao = refreshTokenDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        // Access 토큰 존재하는 경우 처리
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                Jws<Claims> claims = jwtUtil.validateToken(token);
                return true;
            } catch (JwtException e) {
                return handleTokenRefresh(request, response);   // Access token 만료 
            }
        }

        return true;
    }

    // Access token 만료 시 Refresh token 사용하여 Access token 재발급
    private boolean handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("만료");
    	
        try {
            Cookie[] cookies = request.getCookies();
            String refreshToken = null;
            
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }
            
            System.out.println("refresht : " + refreshToken);
            

            // DB에서 Refresh Token 확인
            int tokenCount = refreshTokenDao.findByToken(refreshToken);
            System.out.println(tokenCount);
            if (tokenCount == 0) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            
            System.out.println(tokenCount);

            String userId = jwtUtil.getUserIdFromToken(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(userId);

            // 401 상태코드와 함께 새로운 Access Token을 헤더에 포함
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("New-Access-Token", newAccessToken);
            response.setHeader("Access-Control-Expose-Headers", "New-Access-Token");
            response.setHeader("Token-Expired", "true");

            System.out.println("변화된 토큰 : " + newAccessToken);
            
            return false; // 요청 중단

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}


