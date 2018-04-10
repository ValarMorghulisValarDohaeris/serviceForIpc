package com.punuo.service;

import com.punuo.bean.User;
import com.punuo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lenovo on 2018/4/8.
 * @version 1.0
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserByUserName(String userName){
        return userMapper.getUserByUserName(userName);
    }

    public String getMaxUserId(){return userMapper.getMaxUserId();}

    public Integer saveUser(User user){
        userMapper.saveUser(user);
        return user.getId();
    }
}
