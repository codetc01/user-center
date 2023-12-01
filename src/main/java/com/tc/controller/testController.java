package com.tc.controller;

import com.tc.mapper.UserMapper;
import com.tc.result.Result;
import com.tc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/11/14 23:39
 */
@RestController
public class testController {
    @Autowired
    private UserService userService;

    @GetMapping("/tc")
    public Result test(String username, String password, String checkPassword){
        // userService.register(username, password, checkPassword);
        return Result.success();
    }
}
