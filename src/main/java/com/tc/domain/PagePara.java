package com.tc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/13 22:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagePara {

    private Integer pageSize = 1;

    private Integer pageNum = 10;
}
