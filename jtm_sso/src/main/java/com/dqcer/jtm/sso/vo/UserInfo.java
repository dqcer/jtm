package com.dqcer.jtm.sso.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dongQin
 * @Date: 2018/11/9 13:37
 * @Description: 用户信息
 */
@Data
public class UserInfo implements Serializable {

    private String id;

    private String userName;

    private String userEmail;

    private String password;

    private String salt;

    private String state;

    private String createTime;

    private String updateTime;

    private String invalidTime;

    private String invalidUser;

    private String invalidReason;

    private String invalidReasonDesc;
}
