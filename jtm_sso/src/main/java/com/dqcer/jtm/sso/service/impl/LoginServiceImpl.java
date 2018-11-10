package com.dqcer.jtm.sso.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dqcer.jtm.core.constants.SystemConstants;
import com.dqcer.jtm.core.constants.SystemError;
import com.dqcer.jtm.core.util.Result;
import com.dqcer.jtm.sso.dao.LoginDao;
import com.dqcer.jtm.sso.service.LoginService;
import com.dqcer.jtm.sso.util.IpUtil;
import com.dqcer.jtm.sso.vo.UnifySession;
import com.dqcer.jtm.sso.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public Result authLogin(JSONObject jsonObject, HttpServletRequest request) {
        String username = jsonObject.getString("username");
        String  password = jsonObject.getString("password").trim();
        String remoteRealIP = IpUtil.getRemoteRealIP(request);

        log.debug("用户 {} 登录,登录地址: {}",username, remoteRealIP);
        UserInfo userInfo = loginDao.getInfo(username, password);

        if (null != userInfo) {
             if (SystemConstants.STATE_DISABLE.equals(userInfo.getState())){
                 return new Result(SystemError.ACCOUNT_STOP.getCode(),SystemError.ACCOUNT_STOP.getMessage());
             }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(password, userInfo.getPassword());
            if (matches){
                UnifySession unifySession = new UnifySession();
                unifySession.setToken(BCrypt.gensalt());
                unifySession.setRemoteIp(remoteRealIP);
                unifySession.setStatus("1");
                unifySession.setUserCode(userInfo.getId());
                unifySession.setStartTime(new Date());
                loginDao.addSession(unifySession);
                return new Result(unifySession);
            }
        }
        log.error(SystemError.INVALID_AUTH_INFO.getMessage());
        return new Result(SystemError.INVALID_AUTH_INFO.getCode(),SystemError.INVALID_AUTH_INFO.getMessage());
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "123456";
        //$2a$10$3WwaIqND15MUO7ewD0Qfbu
        String gensalt = BCrypt.gensalt();
        System.out.println(gensalt);
        //$2a$10$3WwaIqND15MUO7ewD0Qfbu4ktdpJIdMw2/p7BGDeIO/toM6FHBKuK
        String hashpw = BCrypt.hashpw(password, gensalt);
        System.out.println(hashpw);

        BCryptPasswordEncoder bCryptPasswordEncoder1 = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$3WwaIqND15MUO7ewD0Qfbu4ktdpJIdMw2/p7BGDeIO/toM6FHBKuK");
        System.out.println(matches);
        System.out.println(BCrypt.gensalt());
        System.out.println();

    }

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public UserInfo getUser(String userName, String password) {
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
