package com.dqcer.jtm.server.controller;

import com.dqcer.jtm.server.model.UserInfo;
import com.dqcer.jtm.server.service.UserService;
import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.login.SsoWebLoginHelper;
import com.dqcer.sso.core.store.SsoLoginStore;
import com.dqcer.sso.core.store.SsoSessionIdHelper;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.ReturnT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: dongQin
 * @Date: 2018/12/8 16:38
 * @Description: 单点登录-基于cookie项目
 */
@Controller
public class WebController {

    @Autowired
    private UserService userService;

    /**
     * 根路径跳转到登录页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

        // login check
        SsoUser ssoUser = SsoWebLoginHelper.loginCheck(request, response);
        if (ssoUser == null) {
            return "redirect:/login";
        }
        final String attributeName = "ssoUser";
        model.addAttribute(attributeName, ssoUser);
        return "index";

    }

    /**
     * 登录页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(Conf.SSO_LOGIN)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

        // login check
        SsoUser ssoUser = SsoWebLoginHelper.loginCheck(request, response);

        if (ssoUser != null) {

            // success redirect
            String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
            if (redirectUrl!=null && redirectUrl.trim().length() > 0) {

                String sessionId = SsoWebLoginHelper.getSessionIdByCookie(request);
                String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;

                return "redirect:" + redirectUrlFinal;
            }
            return "redirect:/";
        }

        model.addAttribute("errorMsg", request.getParameter("errorMsg"));
        model.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
        return "login";
    }

    /**
     * Login
     *
     * @param request
     * @param redirectAttributes
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          RedirectAttributes redirectAttributes,
                          String username,
                          String password,
                          String ifRemember) {

        boolean ifRem = (ifRemember!=null&&"on".equals(ifRemember))?true:false;

        // valid login
        ReturnT<UserInfo> result = userService.findUser(username, password);
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            redirectAttributes.addAttribute("errorMsg", result.getMsg());

            redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
            return "redirect:/login";
        }

        // 1、make xxl-sso user
        SsoUser xxlUser = new SsoUser();
        xxlUser.setUserid(String.valueOf(result.getData().getUserid()));
        xxlUser.setUsername(result.getData().getUsername());
        xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        xxlUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        xxlUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、make session id
        String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

        // 3、login, store storeKey + cookie sessionId
        SsoWebLoginHelper.login(response, sessionId, xxlUser, ifRem);

        // 4、return, redirect sessionId
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        if (redirectUrl!=null && redirectUrl.trim().length()>0) {
            String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;
            return "redirect:" + redirectUrlFinal;
        }
        return "redirect:/";
    }

    /**
     * Logout
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(Conf.SSO_LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        // logout
        SsoWebLoginHelper.logout(request, response);

        redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
        return "redirect:/login";
    }

}
