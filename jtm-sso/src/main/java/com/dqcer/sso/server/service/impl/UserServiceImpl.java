package com.dqcer.sso.server.service.impl;

import com.dqcer.jtm.core.util.ReturnT;
import com.dqcer.sso.server.model.UserInfo;
import com.dqcer.sso.server.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:21
 * @Description:
 */

@Service
public class UserServiceImpl implements UserService{


    /**
     * 效验用户输入的信息是否正确
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ReturnT<UserInfo> findUser(String username, String password) {

        if (StringUtils.isEmpty(username)){
            return new ReturnT<>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (StringUtils.isEmpty(password)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "Please input password.");
        }
        //  效验用户数据
        if ("admin".equals(username) && "123456".equals(password)){
            return new ReturnT<>(new UserInfo(1,username,password));
        }
        return new ReturnT<>(ReturnT.FAIL_CODE, "username or password is invalid.");
    }
}
