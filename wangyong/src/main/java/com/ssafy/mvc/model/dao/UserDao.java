package com.ssafy.mvc.model.dao;

import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.dto.UserDetail;

public interface UserDao {
    int insertUser(User user);
    User findUserByIdUser(String userId);
    LoginRequest findUserByIdLogin(String userId);
    int updateUser(User originUser);
    int updateUserDetail(UserDetail userDetail);
}
