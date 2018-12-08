package com.dqcer.jtm.sso.token.controller;

import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxueli 2017-08-01 21:39:47
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public ReturnT<SsoUser> index(HttpServletRequest request) {
        SsoUser xxlUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<SsoUser>(xxlUser);
    }

}