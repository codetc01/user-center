package com.tc.controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.tc.domain.DTO.LoginDTO;
import com.tc.domain.DTO.RegisterDTO;
import com.tc.domain.User;
import com.tc.domain.VO.UserVo;
import com.tc.result.Result;
import com.tc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.schema.Collections;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/11/15 15:06
 */
@RestController
@RequestMapping("api/user")
@Api(tags = "用户注册登录")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<Long> userRegister(@RequestBody RegisterDTO registerDTO){

        if(registerDTO == null){
            return Result.error("错误");
        }

        Long register = userService.register(registerDTO.getUserAccount(), registerDTO.getPassword(), registerDTO.getCheckPassword(), registerDTO.getPlanetCode());
        return Result.success(register);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<User> userLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest){

        if(loginDTO == null){
            return null;
        }

        User user = userService.userLoginIn(loginDTO.getUserAccount(), loginDTO.getPassword(), httpServletRequest);
        return Result.success(user);
    }

    @GetMapping("/search")
    @ApiOperation("根据用户名查询")
    public Result<List<User>> searchUser(String username, HttpServletRequest httpServletRequest){
        if(!StringUtils.isNotBlank(username)){
            return null;
        }
        List<User> users = userService.searchUser(username, httpServletRequest);
        return Result.success(users);
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public Result deleteUser(@RequestBody Long id, HttpServletRequest httpServletRequest){
        if(id == null){
            return Result.error("前端传入异常");
        }

        Boolean back = userService.deleteUser(id, httpServletRequest);
        return Result.success();
    }

    @PostMapping("/logout")
    @ApiOperation("用户注销")
    public Result userLogout(HttpServletRequest httpServletRequest){
        if(httpServletRequest == null){
            return Result.error("前端传入异常");
        }

        Integer back = userService.userLogout(httpServletRequest);
        return Result.success();
    }

    @GetMapping("/search/tags")
    @ApiOperation("根据标签查询用户")
    public Result searchByTags(@RequestParam(value = "tagNameList", required = false) List<String> users){
        System.out.println(users);
        if(CollectionUtils.isEmpty(users)){
            throw new RuntimeException("用户列表为空");
        }
        List<UserVo> users1 = userService.searchUserByTags(users);
        System.out.println(users1);
        System.out.println(Result.success(users1));
        return Result.success(users1);
    }

    @GetMapping("/current")
    @ApiOperation("/个人主页")
    public Result<UserVo> getCurrentUser(HttpServletRequest request){
        UserVo user = userService.getCurrentUser(request);
        return Result.success(user);
    }

    @PostMapping("/update")
    @ApiOperation("修改用户信息")
    public Result<Integer> editUser(@RequestBody User user, HttpServletRequest httpServletRequest){
        if(user == null){
            throw new RuntimeException("传入对象为空");
        }
        System.out.println("GetUser" + user);
        Integer num = userService.editUser(user, getUser(httpServletRequest));
        return Result.success(num);
    }

    public UserVo getUser(HttpServletRequest request){
        UserVo currentUser = userService.getCurrentUser(request);
        return currentUser;

    }
}
