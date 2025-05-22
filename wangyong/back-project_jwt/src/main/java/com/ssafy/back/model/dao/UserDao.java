package com.ssafy.back.model.dao;

import java.util.Map;

import com.ssafy.back.model.dto.User;

public interface UserDao {
	public User selectOne(Map<String, String> info);
}
