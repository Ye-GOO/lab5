package com.dgut.lab5.service;

import com.dgut.lab5.bean.User;
import com.dgut.lab5.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User find(String account,String password){
        return userMapper.find(account,password);
    }
    public User findByAccount(String account){
        return userMapper.findByAccount(account);
    }
    public void update(User user){
        userMapper.update(user);
    }
    public List<User> findAllUser(){
        return userMapper.findAllUser();
    }

    public User findUserWithClockListByAccount(String account){
        return userMapper.findUserWithClockListByAccount(account);
    }

    public List<User> findFeverUserWithClockListByDate(Date date,double temperature){
        return userMapper.findFeverUserWithClockListByDate(date,temperature);
    }

    public List<User> findKeyAreaUserWithClockListByDate(Date date){
        return userMapper.findKeyAreaUserWithClockListByDate(date);
    }
}
