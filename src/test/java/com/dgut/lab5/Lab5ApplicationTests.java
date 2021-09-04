package com.dgut.lab5;

import com.dgut.lab5.mapper.UserMapper;
import com.dgut.lab5.service.RiskAreaService;
import com.dgut.lab5.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lab5ApplicationTests {


    @Autowired
    RiskAreaService riskAreaService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        riskAreaService.copy();
    }

    //public List<User> findFeverUserWithClockListByDate(Date date,Integer temperature);
}
