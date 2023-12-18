package com.tc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tc.domain.Team;
import com.tc.domain.User;

/**
* @author Administrator
* @description 针对表【team】的数据库操作Service
* @createDate 2023-12-13 15:39:09
*/
public interface TeamService extends IService<Team> {

    public Long addTeam(Team team, User user);

}
