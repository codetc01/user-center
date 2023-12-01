package com.tc.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/1 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Integer id;


    private String username;

    /**
     *
     */
    private String userAccount;

    /**
     *
     */
    private String avatarUrl;

    /**
     *
     */
    private Integer gender;


    /**
     *
     */
    private String phone;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private Integer userStatus;

    /**
     * 用户角色 0 - 用户  1 - 管理员
     */
    private Integer userRole;

    /**
     *
     */
    private LocalDateTime createTime;


    /**
     * 星球ID
     */
    private String planetCode;

    private String tags;

    /**
     * 个人简介
     */
    private String profile;
}
