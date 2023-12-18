package com.tc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tc.Exception.BaseException;
import com.tc.domain.Team;
import com.tc.domain.User;
import com.tc.domain.UserTeam;
import com.tc.domain.enums.TeamStatusEnums;
import com.tc.mapper.TeamMapper;
import com.tc.service.TeamService;
import com.tc.service.UserTeamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
* @author Administrator
* @description 针对表【team】的数据库操作Service实现
* @createDate 2023-12-13 15:39:09
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService {

    @Autowired
    private UserTeamService userTeamService;

    @Override
    @Transactional
    public Long addTeam(Team team, User user) {

        // 请求参数为空校验
        if(team == null){
            throw new RuntimeException("请求参数为空");
        }

        // 是否登录校验，在外边获取user对象时完成校验

        // 队伍人数大于1
        Integer integer = Optional.ofNullable(team.getMaxNum()).orElse(0);
        if(integer < 1 || integer > 20){
            throw new RuntimeException("队伍人数不合法");
        }

        // 队伍标题
        if(!StringUtils.isNotBlank(team.getName()) || team.getName().length() > 20 || team.getName().length() < 1){
            throw new RuntimeException("队名不合法");
        }

        // 描述
        if(StringUtils.isNotBlank(team.getDescription()) && team.getDescription().length() > 512){
            throw new RuntimeException("队伍描述不合法");
        }

        // status是否公开
        TeamStatusEnums status = TeamStatusEnums.getStatus(team.getStatus());
        if(status == null){
            throw new RuntimeException("队伍状态不合法");
        }

        if(status.equals(TeamStatusEnums.SECRET_STATUS) && (StringUtils.isBlank(team.getPassword()) || team.getPassword().length() > 32)){
            throw new RuntimeException("队伍状态不合法");
        }

        if(new Date().after(team.getExpireTime())){
            throw new BaseException("队伍时间不合法");
        }

        // 校验用户最多创建五个队伍,查询team表
        if(this.count(new QueryWrapper<Team>().eq("userId", team.getUserId())) >= 5){
            throw new RuntimeException("已达到队伍最大创建数量");
        }

        // 插入队伍信息到队伍表
        boolean save = this.save(team);
        if(!save){
            throw new RuntimeException("添加失败");
        }

        // 插入用户 ==> 队伍到关系表
        UserTeam userTeam = new UserTeam();
        userTeam.setUserId(user.getId());
        userTeam.setTeamId(team.getId());
        userTeam.setJoinTime(LocalDateTime.now());
        boolean save1 = userTeamService.save(userTeam);
        if(!save1){
            throw new RuntimeException("添加失败");
        }

        return 1L;
    }
}




