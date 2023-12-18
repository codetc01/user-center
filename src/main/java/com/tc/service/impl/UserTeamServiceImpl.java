package com.tc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc.domain.UserTeam;
import com.tc.service.UserTeamService;
import com.tc.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_team】的数据库操作Service实现
* @createDate 2023-12-13 15:45:44
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




