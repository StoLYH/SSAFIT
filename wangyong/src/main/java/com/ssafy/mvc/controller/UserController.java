package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	
	

}
