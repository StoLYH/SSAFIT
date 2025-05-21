package com.ssafy.back.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.back.jwt.JwtUtil;
import com.ssafy.back.model.dto.User;
import com.ssafy.back.model.service.UserService;

@RestController
@RequestMapping("/api-user")
@CrossOrigin("*")
public class UserRestController {
	
	@Autowired
	private UserService service;
	@Autowired
	private JwtUtil util;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
		System.out.println(user);
		
		User tmp = service.login(user.getId(), user.getPassword());
		
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		
		
		//로그인 성공!
		if(tmp != null) {
			status = HttpStatus.ACCEPTED;
			result.put("message", "login 성공");
			result.put("access-token", util.createToken(tmp.getName()));
		}else {
			//로그인 실패!
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(result, status);
	}
}
