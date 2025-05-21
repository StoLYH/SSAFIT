package com.ssafy.mvc.controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
		// 로그인 로직 처리
		System.out.println("check");
		LoginRequest user = userService.login(loginRequest);	// 로그인 성공~
		
		// 토큰 발행
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		
		// 로그인 성공
		if (user != null) {
			status = HttpStatus.ACCEPTED;
			result.put("userId", user.getUserId());	// 사용자 id 
			result.put("access-token", jwtUtil.createToken(user.getUserId()));
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(result, status);	// return new ResponseEntity<>(body, status);
		
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 성공");
	}
}