package com.dqcer.jtm.sso.web.controller;

import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: dongQin
 * @Date: 2018/12/8 18:01
 * @Description: 基于web方式的客户端
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){

        SsoUser ssoUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        model.addAttribute("ssoUser", ssoUser);
        return "index";
    }

    @RequestMapping("/json")
    @ResponseBody
    public ReturnT<SsoUser> json(Model model, HttpServletRequest request) {
        SsoUser xxlUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT(xxlUser);
    }
}
