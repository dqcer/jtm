package com.dqcer.jtm.sso.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.core.util.Result;
import com.dqcer.jtm.sso.service.LoginService;

public class LoginServiceImpl implements LoginService {


    /**
     * 效验口令和密码
     *
     * @param jsonObject
     * @return
     */
    @Override
    public Result authLogin(JSONObject jsonObject) {
        return null;
    }

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Result getUser(String userName, String password) {
        return null;
    }

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
    @Override
    public Result getInfo() {
        return null;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public Result logout() {
        return null;
    }
}
