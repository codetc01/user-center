package com.tc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tc.domain.Team;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【team】的数据库操作Mapper
* @createDate 2023-12-13 15:39:09
* @Entity generator.domain.Team
*/
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}




