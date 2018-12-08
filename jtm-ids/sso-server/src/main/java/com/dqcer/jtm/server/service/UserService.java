package com.dqcer.jtm.server.service;

import com.dqcer.jtm.server.model.UserInfo;
import com.dqcer.sso.core.util.ReturnT;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:07
 * @Description: 单点服务-业务
 */
public interface UserService {

    /**
     * 效验用户输入的信息是否正确
     *
     * @param username
     * @param password
     * @return
     */
    ReturnT<UserInfo> findUser(String username, String password);


}
