package com.tc.domain.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/11/15 15:20
 */
@Data
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = -7584644447937395780L;

    private String userAccount;

    private String password;

    private String checkPassword;

    private String planetCode;
}
