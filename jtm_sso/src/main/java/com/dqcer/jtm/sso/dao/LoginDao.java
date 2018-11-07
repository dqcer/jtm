package com.dqcer.jtm.sso.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: dongQin
 * @Date: 2018/11/7 8:55
 * @Description: 登录dao层
 */

@Repository
public interface LoginDao {

    /**
     * 根据用户名和密码查询信息
     *
     * @param userName
     * @param password
     * @return
     */
    JSONObject getInfo(@Param("userName")String userName, @Param("password")String password);
}
