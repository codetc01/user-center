package com.tc.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/11 22:56
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfiguration {

    private String host;

    private String port;

    private String password;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String address = String.format("redis://%s:%s", host, port);
        System.out.println("112711" + address);
        config.useSingleServer().setAddress(address).setDatabase(3).setPassword(password);

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        return redisson;

    }

}
