package com.ssafy.back.interceptor;

import java.util.concurrent.LinkedTransferQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.back.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	
	private final String HEADER_AUTH = "access-token";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//클라이언트가 서버에게 요청을 보내려고 했을 때 사전요청을 한번 보낸다.
		if(request.getMethod().equals("OPTIONS"))
			return true;
		
		
		String token = request.getHeader(HEADER_AUTH);
		
		if(token != null) {
			jwtUtil.vaildate(token);
			return true;
		}
		
		throw new Exception("유효하지 않은 접근입니다.");
	}
}
