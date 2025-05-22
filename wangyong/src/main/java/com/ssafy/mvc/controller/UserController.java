package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
public class UserController {

    //싱글톤 의존성 주입
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    //Post 방식 회원가입 json형식으로 넘어올 때 DB까지 전달
    @PostMapping
    public ResponseEntity<String> registUser(
        @RequestParam("userId") String userId,
        @RequestParam("userName") String userName,
        @RequestParam("userRole") String userRole,
        @RequestParam("password") String password,
        @RequestParam(value = "attach", required = false) MultipartFile attach
    ) throws IOException {
        System.out.println("userId: " + userId);
        System.out.println("userName: " + userName);
        System.out.println("userRole: " + userRole);
        System.out.println("password: " + password);

        User user = new User(userId, userName, userRole, password);
        user.setAttach(attach);
        if (userService.insertUser(user) == 1) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.NO_CONTENT);
    }

    //Put방식으로 유저 정보 수정


    @PutMapping("{userId}") // ✅ 경로 변수는 반드시 {} 안에 써야 한다!
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable("userId") String userId) {
        if(userService.updateUser(user,userId)==1) {
            return ResponseEntity.ok("success");

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found or update failed");
    }
    

    /**
     * 회원가입 시 -> id 중복성 검사
     */
    @GetMapping("confirmId/{id}")
    public ResponseEntity<String> confirm1(@PathVariable("id") String id) {
    	
    	try {
        	boolean result = userService.confirmId(id);
        	if (result == true) { 
        		return ResponseEntity.status(HttpStatus.OK).body("비중복");
        	} else {
        		return ResponseEntity.status(HttpStatus.OK).body("중복");
        	}
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	} 
    }

        	
    	
    /**
     * 회원가입 시 -> Name 중복성 검사
     */
    @GetMapping("confirmName/{name}")
    public ResponseEntity<String> confirm2(@PathVariable("name") String name) {

    	try {
        	boolean result = userService.confirmName(name);
        	if (result == true) { 
        		return ResponseEntity.status(HttpStatus.OK).body("비중복");
        	} else {
        		return ResponseEntity.status(HttpStatus.OK).body("중복");
        	}
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	} 
    }
    
    
}
