package com.tc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tc.domain.DTO.TeamQueryDTO;
import com.tc.domain.PagePara;
import com.tc.domain.Team;
import com.tc.domain.User;
import com.tc.result.Result;
import com.tc.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/13 22:33
 */
@RestController
@RequestMapping("/team")
@Slf4j
@Api(tags = "组队接口")
public class teamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/add")
    @ApiOperation("添加团队")
    public Result addTeam(@RequestBody Team team, HttpServletRequest httpServletRequest){
        if(team == null){
            throw new RuntimeException("传入参数为空");
        }

        User userLoginState = (User) httpServletRequest.getSession().getAttribute("userLoginState");
        if(userLoginState == null){
            throw new RuntimeException("用户未登录");
        }

        Long aLong = teamService.addTeam(team, userLoginState);

        if(aLong == null){
            throw new RuntimeException("添加失败");
        }
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除团队")
    public Result deleteTeam(Long id){
        if(id == null){
            throw new RuntimeException("传入参数为空");
        }

        boolean deleteResult = teamService.removeById(id);

        if(!deleteResult){
            throw new RuntimeException("添加失败");
        }

        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新团队")
    public Result updateTeam(@RequestBody Team team){
        if(team == null){
            throw new RuntimeException("传入参数为空");
        }

        boolean update = teamService.updateById(team);
        // boolean update = teamService.update(team, new QueryWrapper<Team>().eq("id", team.getId()));

        if(!update){
            throw new RuntimeException("添加失败");
        }

        return Result.success();
    }

    @GetMapping("/get")
    @ApiOperation("根据ID查询")
    public Result<Team> getById(Long id){
        if(id == null){
            throw new RuntimeException("传入参数为空");
        }

        Team byId = teamService.getById(id);
        return Result.success(byId);
    }

    @GetMapping("/list")
    @ApiOperation("查询列表1")
    public Result<List<Team>> getByList(@RequestBody TeamQueryDTO teamQueryDTO){
        if(teamQueryDTO == null){
            throw new RuntimeException("传入参数为空");
        }
        Team team = new Team();
        BeanUtils.copyProperties(team, teamQueryDTO);
        List<Team> list = teamService.list(new QueryWrapper<>(team));
        return Result.success(list);
    }

    @GetMapping("/list/page")
    @ApiOperation("查询列表")
    public Result<Page<Team>> getByListPage(@RequestBody TeamQueryDTO teamQueryDTO){
        if(teamQueryDTO == null){
            throw new RuntimeException("传入参数为空");
        }
        Page<Team> page = new Page<>(teamQueryDTO.getPageNum(), teamQueryDTO.getPageSize());
        Team team = new Team();
        BeanUtils.copyProperties(team, teamQueryDTO);
//        List<Team> list = teamService.list(page, new QueryWrapper<>(team));
        Page<Team> page1 = teamService.page(page, new QueryWrapper<>(team));
        return Result.success(page1);
    }
}
