package com.dqcer.sso.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:19
 * @Description: 用户信息
 */
@AllArgsConstructor
@Data
public class UserInfo {

    private int userid;
    private String username;
    private String password;
}
