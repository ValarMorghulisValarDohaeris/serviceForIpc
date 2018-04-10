package com.punuo.mapper;

import com.punuo.bean.User;

/**
 * @author lenovo on 2018/4/8.
 * @version 1.0
 */

public interface UserMapper {
    //根据userName查找对应的User
    User getUserByUserName(String userName);

    //获取id最大的user的userId,记为a；新增用户时分配的userId为a+1
    String getMaxUserId();

    //保存用户
    Integer saveUser(User user);

}
