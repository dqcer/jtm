package com.dqcer.jtm.sso.service;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.core.util.Result;
import com.dqcer.jtm.sso.vo.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

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
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Result authLogin(JSONObject jsonObject, HttpServletRequest request);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param userName
     * @param password
     * @return
     */
    UserInfo getUser(String userName, String password);

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
    Result getInfo();

    /**
     * 退出登录
     *
     * @return
     */
    Result logout();

}
