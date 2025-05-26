package com.ssafy.mvc.controller;

import com.ssafy.mvc.exception.BoardException;
import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserDetail;
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
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }



    //Post 방식 회원가입 json형식으로 넘어올 때 DB까지 전달
    @PostMapping
    public ResponseEntity<String> registUser(
        @RequestParam("userId") String userId,
        @RequestParam("userName") String userName,
        @RequestParam("userRole") int userRole,
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


    //유저정보가져오기
    @GetMapping("{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable String userId , @RequestHeader(value = "Authorization", required = false) String token) {

    	 if (token != null && token.startsWith("Bearer ")) {
    		 String tokenUserId = jwtUtil.getUserIdFromToken(token);
    		 boolean isOwner = tokenUserId.equals(userId);
             User user = userService.getUserInfo(userId);
             user.setEditable(isOwner);
             return ResponseEntity.status(HttpStatus.OK).body(user);
    	 } else {
    		 User user = userService.getUserInfo(userId);
             user.setEditable(false);
             return ResponseEntity.status(HttpStatus.OK).body(user);
    	 }
    	
    	
    }


    @PutMapping("{userId}")
    public ResponseEntity<String> updateUser(
            @RequestParam("userName") String userName,
            @RequestParam("userRole") int userRole,
            @RequestParam("onelineInfo") String onelineInfo,  // userDetail 필드
            @RequestParam("exper") String exper,              // userDetail 필드
            @RequestParam(value = "attach", required = false) MultipartFile attach,
            @PathVariable("userId") String userId
    ) throws IOException {
        User user = new User();
        user.setUserName(userName);
        user.setUserRole(userRole);

        UserDetail userDetail = new UserDetail();
        userDetail.setOnelineInfo(onelineInfo);
        userDetail.setExper(exper);
        user.setUserDetail(userDetail);
        user.setAttach(attach);


        if(userService.updateUser(user, userId) == 1) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found or update failed");
    }


    //디테일 정보등록
     @PutMapping("detail")
  public ResponseEntity<?> updateUserDetail(@RequestBody UserDetail userDetail) {
         System.out.println("userDetail: " + userDetail);
      userService.updateUserDetail(userDetail);
      return ResponseEntity.ok().build();
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
