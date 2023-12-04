package com.tc.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.domain.User;
import com.tc.mapper.UserMapper;
import com.tc.service.UserService;
import com.tc.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService1;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void Test(){
        // System.out.println(userService.register("tian^cheng", "12345678", "12345678"));
    }

    private ExecutorService executorService = new ThreadPoolExecutor(40, 1000, 1000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));

    @Test
    public void Test1(){
        String regex = "[!@#$%^&*(),.?\":{}|<>\\s]";
        System.out.println(Pattern.compile(regex).matcher("tian cheng").find());
    }

    @Test
    public void select(){
        Page<User> userPage = userMapper.selectPage(new Page<>(1, 2), null);
        System.out.println(userPage.getRecords());
    }
}