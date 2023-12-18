package com.tc.mapper;

import com.tc.domain.UserTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【user_team】的数据库操作Mapper
* @createDate 2023-12-13 15:45:44
* @Entity com.tc.domain.UserTeam
*/
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}




