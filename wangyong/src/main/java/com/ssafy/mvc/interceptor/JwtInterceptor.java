package com.ssafy.mvc.interceptor;


import com.ssafy.mvc.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private JwtUtil jwtUtil;
    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //토큰이 저장된 Authorizaiton를 헤더에서 뽑아서 authHeader에 넣기
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            //앞에있는 Bearer 일곱글자 제거하는것임
            try {
                String token = authHeader.substring(7);
                Jws<Claims> jwsClaims = jwtUtil.validateToken(token);

                String userId = jwsClaims.getPayload().getSubject();

                request.setAttribute("userId", userId);
                return true;
            } catch (JwtException e) {
                // 유효하지 않은 토큰
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return false;
            }
        }
        // 토큰 없거나 잘못된 형식
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authorization header missing or malformed");
        return false;
    }
}


