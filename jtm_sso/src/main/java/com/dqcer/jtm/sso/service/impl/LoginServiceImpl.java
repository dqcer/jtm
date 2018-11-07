package com.dqcer.jtm.sso.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.sso.dao.LoginDao;
import com.dqcer.jtm.sso.service.LoginService;
import com.dqcer.jtm.sso.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: dongQin
 * @Date: 2018/11/7 8:55
 * @Description: 登录业务处理类
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private LoginDao loginDao;

    /**
     * 效验口令和密码
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String  password = jsonObject.getString("password");
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        JSONObject result = new JSONObject();
        try {
            currentUser.login(token);
            result.put("result","success");
        }catch (AuthenticationException auth){
            result.put("result","fail");
            log.error("认证失败");
        }

        return CommonUtil.success(result);
    }

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public JSONObject getUser(String userName, String password) {
        return loginDao.getInfo(userName, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     *
     * @return
     */
    @Override
    public JSONObject getInfo() {
        return null;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public JSONObject logout() {
        return null;
    }
}
