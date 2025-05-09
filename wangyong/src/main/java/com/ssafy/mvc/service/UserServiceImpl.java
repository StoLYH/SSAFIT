package com.ssafy.mvc.service;
import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //싱글톤 의존성 주입
    private UserDao userDao;
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }
}
