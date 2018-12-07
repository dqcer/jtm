package com.dqcer.sso.server.controller;

import com.dqcer.jtm.core.util.ReturnT;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.server.model.UserInfo;
import com.dqcer.sso.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:06
 * @Description: 单点登录-app
 */

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ReturnT<UserInfo> login(String username, String password) {
        ReturnT<UserInfo> returnT = userService.findUser(username, password);
        if (returnT.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<>(returnT.getCode(), returnT.getMsg());
        }

        // 新增ssoUser
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(String.valueOf(returnT.getData().getUserid()));
        ssoUser.setUsername(returnT.getData().getUsername());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        ssoUser.setExpireFreshTime(System.currentTimeMillis());
    }
}
