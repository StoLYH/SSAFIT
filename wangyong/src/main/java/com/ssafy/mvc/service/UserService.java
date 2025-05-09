package com.ssafy.mvc.service;

import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;

public interface UserService {
    public int insertUser(User user);
	public LoginRequest login(LoginRequest loginRequest);
}
