package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {

    //싱글톤 의존성 주입
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    //Post 방식 회원가입 json형식으로 넘어올 때 DB까지 전달
    @PostMapping
    public ResponseEntity<String> registUser(@RequestBody User user) {
        if(userService.insertUser(user)==1){
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        //임시
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
    
    

	
	

}
