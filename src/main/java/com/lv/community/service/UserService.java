package com.lv.community.service;

import com.lv.community.mapper.UserMapper;
import com.lv.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lvjiangtao
 * @create 2021-06-14-16:10
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccount_id());
        if (dbUser == null){
            //数据库中没有 account_id 一样的就插入到数据库中
            userMapper.addUser(user);
        }else {
            dbUser.setAvatar_url(user.getAvatar_url());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());

            userMapper.update(dbUser);
        }

    }
}
