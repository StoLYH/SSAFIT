package com.ssafy.mvc.service;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.LoginRequest;
import com.ssafy.mvc.model.dto.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	// 싱글톤 의존성 주입
	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public LoginRequest login(LoginRequest loginRequest) {
		// service에서 로그인로직 처리
		LoginRequest user = userDao.findUserById(loginRequest.getUserId());
		System.out.println(user.getUserId()+" "+user.getPassword());
		if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
			throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다");
		} else {
			return user;
		}
	}
}
