package com.dqcer.jtm.sso.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: dongQin
 * @Date: 2018/11/5 14:20
 * @Description: 登录接口
 */

public interface LoginService {

    /**
     * 效验口令和密码
     *
     * @param jsonObject
     * @return
     */
    JSONObject authLogin(JSONObject jsonObject);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param userName
     * @param password
     * @return
     */
    JSONObject getUser(String userName, String password);

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
    JSONObject getInfo();

    /**
     * 退出登录
     *
     * @return
     */
    JSONObject logout();

}
