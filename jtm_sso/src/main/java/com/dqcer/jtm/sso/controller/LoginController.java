package com.dqcer.jtm.sso.controller;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.core.web.BaseController;
import com.dqcer.jtm.sso.service.LoginService;
import com.dqcer.jtm.sso.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/sso")
public class LoginController extends BaseController{


    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("login")
    public JSONObject login(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequired(jsonObject,"username,password");
        log.error("测试{}","对方答复");
        return loginService.authLogin(jsonObject);
    }

    /**
     * 查询当前登录用户的信息
     *
     * @return
     */
    @PostMapping("/getInfo")
    public JSONObject getInfo(){
        return loginService.getInfo();
    }

    /**
     * 注销/退出
     *
     * @return
     */
    @PostMapping("/logout")
    public JSONObject logout(){
        return loginService.logout();
    }
}
