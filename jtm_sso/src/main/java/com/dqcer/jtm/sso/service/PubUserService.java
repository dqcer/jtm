package com.dqcer.jtm.sso.service;

import com.dqcer.jtm.sso.dao.PubUserDao;
import com.dqcer.jtm.sso.entity.PubUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubUserService {

    @Autowired
    private PubUserDao pubUserDao;

    public PubUserEntity findByUserName(String userName){
        return pubUserDao.findByUserName(userName);
    }



}
