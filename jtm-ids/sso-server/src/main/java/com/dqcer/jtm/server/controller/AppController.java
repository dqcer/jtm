package com.dqcer.jtm.server.controller;

import com.dqcer.jtm.server.model.UserInfo;
import com.dqcer.jtm.server.service.UserService;
import com.dqcer.sso.core.login.SsoTokenLoginHelper;
import com.dqcer.sso.core.store.SsoLoginStore;
import com.dqcer.sso.core.store.SsoSessionIdHelper;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:06
 * @Description: 单点登录-基于token项目
 */

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(String username, String password) {
        ReturnT<UserInfo> returnT = userService.findUser(username, password);
        if (returnT.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<>(returnT.getCode(), returnT.getMsg());
        }

        // 新增ssoUser
        SsoUser xxlUser = new SsoUser();
        xxlUser.setUserid(String.valueOf(returnT.getData().getUserid()));
        xxlUser.setUsername(returnT.getData().getUsername());
        xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        xxlUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        xxlUser.setExpireFreshTime(System.currentTimeMillis());

        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, xxlUser);

        // 4、return sessionId
        return new ReturnT<String>(sessionId);
    }

    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<SsoUser> logincheck(String sessionId) {

        // logout
        SsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (xxlUser == null) {
            return new ReturnT<SsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<SsoUser>(xxlUser);
    }

}
