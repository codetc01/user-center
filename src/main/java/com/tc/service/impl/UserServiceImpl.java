package com.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tc.Exception.RegisterException;
import com.tc.constant.MessageConstant;
import com.tc.domain.User;
import com.tc.domain.VO.UserVo;
import com.tc.mapper.UserMapper;
import com.tc.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-14 16:28:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final static String USER_LOGIN_STATE = "userLoginState";

    @Override
    public Long register(String username, String password, String checkUserWord, String planetCode) {

        if (StringUtils.isAnyEmpty(username, password, checkUserWord)) {
            throw new RegisterException(MessageConstant.INFO_NOT_NULL);
        }

        if(password.length() < 8 || checkUserWord.length() < 8){
            throw new RegisterException(MessageConstant.CHECK_PASSWORD_LENGTH);
        }

        if(username.length() < 4){
            throw new RuntimeException(MessageConstant.CHECK_USERACCOUNT_LENGTH);
        }

        if(planetCode.length() > 5){
            throw new RuntimeException(MessageConstant.PLANETCODE_PASSWORD_LENGTH);
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("planetCode", planetCode);

        Long aLong = userMapper.selectCount(userQueryWrapper);

        if(aLong != 0){
            throw new RegisterException(MessageConstant.PLANET_FOUND);
        }

        String regex = "[!@#$%^&*(),.?\":{}|<>\\s]";
        System.out.println(Pattern.compile(regex).matcher(password).find());
        if(Pattern.compile(regex).matcher(password).find() || Pattern.compile(regex).matcher(username).find()){
            throw new RegisterException(MessageConstant.CHECK_SPECIAL_CHAR);
        }

        if(!password.equals(checkUserWord)){
           throw new RegisterException(MessageConstant.CHECK_PASSWORD);
        }

        if(userMapper.selectCount(new QueryWrapper<User>().eq("userAccount", username)) > 0L){
            throw new RegisterException(MessageConstant.CHECK_USERACCOUNT);
        }

        User build = User.builder()
                .userAccount(username)
                .userPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .build();

        int insert = userMapper.insert(build);

        if(insert == 0){
            throw new RegisterException(MessageConstant.UNKNOWN_ERROR);
        }

        return build.getId();
    }

    @Override
    public User userLoginIn(String username, String password, HttpServletRequest httpServletRequest) {

        if (StringUtils.isAnyEmpty(username, password)) {
            throw new RegisterException(MessageConstant.INFO_NOT_NULL);
        }

        if(password.length() < 8){
            throw new RegisterException(MessageConstant.CHECK_PASSWORD_LENGTH);
        }

        if(username.length() < 4){
            throw new RuntimeException(MessageConstant.CHECK_USERACCOUNT_LENGTH);
        }

        String regex = "[!@#$%^&*(),.?\":{}|<>\\s]";
        System.out.println(Pattern.compile(regex).matcher(password).find());
        if(Pattern.compile(regex).matcher(password).find() || Pattern.compile(regex).matcher(username).find()){
            throw new RegisterException(MessageConstant.CHECK_SPECIAL_CHAR);
        }

        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("userAccount", username);
        objectQueryWrapper.eq("userPassword", DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        User user = userMapper.selectOne(objectQueryWrapper);

        if(user == null){
            throw new RegisterException(MessageConstant.UNKNOWN_ERROR);
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setUserAccount(user.getUserAccount());
        newUser.setAvatarUrl(user.getAvatarUrl());
        newUser.setGender(user.getGender());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setUserStatus(user.getUserStatus());
        newUser.setCreateTime(user.getCreateTime());
        newUser.setUpdateTime(user.getUpdateTime());

        // ????
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE, user);

        return newUser;
    }

    @Override
    public List<User> searchUser(String username, HttpServletRequest httpServletRequest) {
        // 搜索前对用户身份进行校验

        // 获取session对象的user进行身份校验
        User attribute = (User) httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user1 = userMapper.selectById(attribute.getId());
        if(user1.getUserRole() != 1){
            return new ArrayList<User>();
        }


        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("username", username);

        List<User> users = userMapper.selectList(objectQueryWrapper);
        // 在这里对数据进行脱敏
        List<User> userList = new ArrayList<>();
        for (User user : users) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setUserAccount(user.getUserAccount());
            newUser.setAvatarUrl(user.getAvatarUrl());
            newUser.setGender(user.getGender());
            newUser.setPhone(user.getPhone());
            newUser.setEmail(user.getEmail());
            newUser.setUserStatus(user.getUserStatus());
            newUser.setCreateTime(user.getCreateTime());
            newUser.setUpdateTime(user.getUpdateTime());
            userList.add(newUser);
        }
        return userList;
    }

    @Override
    public Boolean deleteUser(Long id, HttpServletRequest httpServletRequest) {

        // 获取session对象的user进行身份校验
        User attribute = (User) httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user1 = userMapper.selectById(attribute.getId());
        if(user1.getUserRole() != 1){
            return false;
        }

        int i = userMapper.deleteById(id);
        if(i == 1){
            return true;
        }
        return null;
    }

    @Override
    public Integer userLogout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public List<UserVo> searchUserByTags(List<String> tagsList) {
        if(tagsList.isEmpty()){
            throw new RegisterException(MessageConstant.UNKNOWN_ERROR);
        }

        // 第一种方式，直接去表里遍历查询，但是如果每个人都去遍历，比较浪费时间
//        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
//        List<User> users = new ArrayList<>();
//        for (String tag : tagsList) {
//            objectQueryWrapper.like("tags", tag);
//        }
////        objectQueryWrapper.like("tags", "java").like("tags", "nihao");
//        users = userMapper.selectList(objectQueryWrapper);
        // 第二种方式，先查到内存中，再从内存中查数据

        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        Gson gson = new Gson();

        List<User> collect = users.stream().filter(user -> {
            String tags = user.getTags();
            if (tags == null) {
                return false;
            }
            Set<String> tagsNameList = gson.fromJson(tags, new TypeToken<Set<String>>() {
            }.getType());
            for (String tagsName : tagsNameList) {
                if (tagsList.contains(tagsName)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());

        List<UserVo> userVos = new ArrayList<>();
        for (User user : collect){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            userVos.add(userVo);
        }

        return userVos;
    }

    @Override
    public UserVo getCurrentUser(HttpServletRequest request) {
        User attribute = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if(attribute == null){
            throw new RuntimeException("Session对象为空");
        }
        User user = userMapper.selectById(attribute.getId());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public Integer editUser(User user, UserVo userVo) {
        if(user.getId() == null){
            throw new RuntimeException("传入的ID为空");
        }
        if (isAdmin(user.getId())) {
            // 管理员接口，可以对所有用户进行操作
            User userByDB = userMapper.selectById(user.getId());
            if(userByDB == null){
                throw new RuntimeException("要修改的用户不存在");
            }
            int i = userMapper.updateById(user);
            return i;
        }

        // 普通用户，要对身份进行校验
        if(userVo.getId() == null){
            throw new RuntimeException("Session对象出错");
        }
        System.out.println("ID" + user.getId() + " " + userVo.getId());
        if(!user.getId().equals(userVo.getId())){
            throw new RuntimeException("无权修改该用户信息");
        }
        User userByDB = userMapper.selectById(user.getId());
        if(userByDB == null){
            throw new RuntimeException("要修改的用户不存在");
        }
        int i = userMapper.updateById(user);
        return i;
    }

    @Override
    public Boolean isAdmin(Long id) {
        if(id == null){
            throw new RuntimeException("传入ID为空");
        }
        User user = userMapper.selectById(id);
        // 如果为0，代表普通用户
        if(user.getUserRole() == 0){
            return false;
        }
        // 为1，则代表是管理员
        return true;
    }


}




