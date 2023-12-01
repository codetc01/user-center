package com.tc.generator.mapper;

import com.tc.domain.User;
import com.tc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        User haha = User.builder()
                .username("haha")
                .userPassword("1234456")
                .userStatus(0)
                .updateTime(LocalDateTime.now())
                .isDelete(0)
                .build();

        userMapper.insert(haha);
        System.out.println(haha.getId());
    }

}