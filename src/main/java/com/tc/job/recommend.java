package com.tc.job;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.domain.User;
import com.tc.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/10 22:08
 */
@Component
public class recommend {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RedissonClient redissonClient;

    @Scheduled(cron = "0 39 * * * *")
    public void execute(){
        RLock lock = redissonClient.getLock("yupao:preCache:Cache:lock");
        // String key = null;

        try {
            if(lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                Thread.sleep(100000);
                String key = String.format("yupao:user:recommend:%s", 8);
                Page<User> userPage = new Page<>(1, 20);
                Page<User> page = userService.page(userPage, null);

                ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                valueOperations.set(key, page, 100000, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
