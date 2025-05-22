package com.ssafy.mvc.controller;

import com.ssafy.mvc.exception.BoardException;
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
    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<User> getUserInfo(@PathVariable String userId) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(userId));

    }


    @PutMapping("{userId}") // ✅ 경로 변수는 반드시 {} 안에 써야 한다!
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable("userId") String userId) {
        if(userService.updateUser(user,userId)==1) {
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
    
    

	
	

}
