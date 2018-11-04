package com.dqcer.jtm.sso.dao;

import com.dqcer.jtm.sso.entity.PubUserEntity;

public interface PubUserDao {
    PubUserEntity findByUserName(String userName);
}
