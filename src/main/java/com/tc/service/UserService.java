package com.tc.service;

import com.tc.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tc.domain.VO.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2023-11-14 16:28:09
*/
public interface UserService extends IService<User> {

    Long register(String username, String password, String checkUserWord, String planetCode);

    User userLoginIn(String username, String password, HttpServletRequest httpServletRequest);

    List<User> searchUser(String username, HttpServletRequest httpServletRequest);

    Boolean deleteUser(Long id, HttpServletRequest httpServletRequest);

    Integer userLogout(HttpServletRequest httpServletRequest);

    List<UserVo> searchUserByTags(List<String> tagsList);
}
