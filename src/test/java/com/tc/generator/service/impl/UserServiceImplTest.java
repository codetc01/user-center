package com.tc.generator.service.impl;

import com.tc.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void Test(){
        // System.out.println(userService.register("tian^cheng", "12345678", "12345678"));
    }

    @Test
    public void Test1(){
        String regex = "[!@#$%^&*(),.?\":{}|<>\\s]";
        System.out.println(Pattern.compile(regex).matcher("tian cheng").find());
    }
}