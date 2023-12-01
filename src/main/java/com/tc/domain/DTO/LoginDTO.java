package com.tc.domain.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/11/15 15:35
 */
@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -5599596656372513768L;

    private String userAccount;

    private String password;
}
