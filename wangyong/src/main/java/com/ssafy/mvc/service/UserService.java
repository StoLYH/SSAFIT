package com.ssafy.mvc.service;

import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;

public interface UserService {
    public int insertUser(User user);
    public int updateUser(User user, String userId);
	public LoginRequest login(LoginRequest loginRequest);
	
	public boolean confirmId(String id);
	public boolean confirmName(String name);

}
