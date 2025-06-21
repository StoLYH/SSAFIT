package com.ssafy.mvc.controller;
import com.ssafy.mvc.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	// 싱글톤 의존성 주입

	@Autowired
	private JwtUtil jwtUtil;

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		// 로그인 로직 처리

		LoginRequest user = userService.login(loginRequest);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
		}


		session.setAttribute("loginUser", user.getUserId());
		// JWT 발급
		String token = jwtUtil.generateToken(user.getUserId());

		// 응답 JSON으로 반환
		Map<String, String> response = new HashMap<>();
		response.put("message", "로그인 성공");
		response.put("token", token);
		response.put("userId", user.getUserId()); // 필요 시 사용자 정보도 같이 보내기

		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 성공");
	}
	
	
	
	
}