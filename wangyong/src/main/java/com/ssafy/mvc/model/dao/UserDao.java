package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;

public interface UserDao {
    int insertUser(User user);
	LoginRequest findUserById(String userId);
}
