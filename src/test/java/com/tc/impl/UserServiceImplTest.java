package com.tc.impl;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.domain.User;
import com.tc.mapper.UserMapper;
import com.tc.service.UserService;
import com.tc.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.rules.Stopwatch;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService1;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redissonClient;

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

    @Test
    public void test1(){
        RList<String> list = redissonClient.getList("test-List");

        list.add("tc");
    }

    // @Test
    public void insertFakeData(){
        StopWatch stopWatch = new StopWatch();
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        stopWatch.start();
        for (int i = 0; i < 20; i++) {
            int j = 0;
            List<User> users = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("fake");
                user.setUserAccount("fakeAccount");
                user.setGender("男");
                user.setUserPassword("12345678");
                user.setPhone("13279011567");
                user.setEmail("123fake@qq.com");
                user.setCreateTime(LocalDateTime.now());
                user.setUpdateTime(LocalDateTime.now());
                user.setTags("[\"java\"]");

                users.add(user);
                if(j == 5000){
                    break;
                }
            }

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(users);
            }, executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println("time" + stopWatch.getTotalTimeMillis());
    }

}