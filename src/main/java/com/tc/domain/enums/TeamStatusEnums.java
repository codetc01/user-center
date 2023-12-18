package com.tc.domain.enums;

/**
 * @PROJECT_NAME: user-center
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2023/12/14 11:38
 */
public enum TeamStatusEnums {

    PUBLIC_STATUS("公开", 0),
    PRIVATE_STATUS("私有", 1),
    SECRET_STATUS("加密", 2);

    private String status;

    private Integer statusCode;


    TeamStatusEnums(String status, Integer statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public static TeamStatusEnums getStatus(Integer statusCode){
        TeamStatusEnums[] values = TeamStatusEnums.values();
        for (TeamStatusEnums value : values) {
            if(value.getStatusCode() == statusCode){
                return value;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
