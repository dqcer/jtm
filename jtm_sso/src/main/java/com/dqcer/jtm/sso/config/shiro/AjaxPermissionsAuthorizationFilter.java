package com.dqcer.jtm.sso.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.sso.util.constants.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: hxy
 * @description: 对没有登录的请求进行拦截, 全部返回json信息. 覆盖掉shiro原本的跳转login.jsp的拦截方式
 * @date: 2017/10/24 10:11
 */
@Slf4j
public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)){
            if (this.isLoginSubmission(request, response)){
                return  this.executeLogin(request,response);
            }else {
                System.out.println("登录页面");
                return true;
            }
        }else {

            System.out.println("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("returnCode", ErrorEnum.E_20011.getErrorCode());
            jsonObject.put("returnMsg", ErrorEnum.E_20011.getErrorMsg());
            PrintWriter out = null;
            HttpServletResponse res = (HttpServletResponse) response;
            try {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                out = response.getWriter();
                out.println(jsonObject);
            } catch (Exception e) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
            return false;
        }
    }

    @Bean
    public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HttpSession session = ((HttpServletRequest)request).getSession();
        System.out.println("登录成功");
      /*  User user = userMapper.selectByUserName(token.getPrincipal().toString());
        session.setAttribute(Const.CURRENT_USER, user);
        ServerResponse serverResponse = ServerResponse.createBySuccessMsg("登录成功");
        Gson gson = GsonFactory.getGson();
        String s = gson.toJson(serverResponse);
        out.println(s);
        out.flush();
        out.close();*/
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("登录失败");
       /* ServerResponse serverResponse = ServerResponse.createByErrorMessage("登录失败");
        Gson gson = GsonFactory.getGson();
        String s = gson.toJson(serverResponse);
        out.println(s);
        out.flush();
        out.close();*/
        return false;
    }


}
