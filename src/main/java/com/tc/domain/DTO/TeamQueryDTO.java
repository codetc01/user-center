package com.tc.domain.DTO;

import com.tc.domain.PagePara;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/13 22:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeamQueryDTO extends PagePara {
    /**
     *
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0-公开 1-私有 2-加密
     */
    private Integer status;

}
