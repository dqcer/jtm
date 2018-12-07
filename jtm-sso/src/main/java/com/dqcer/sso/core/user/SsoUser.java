package com.dqcer.sso.core.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:44
 * @Description: sso user
 */
@Data
public class SsoUser implements Serializable{

    private static final long serialVersionUID = 3690060742354225927L;

    private String userid;
    private String username;
    private Map<String, String> plugininfo;

    private String version;
    private int expireMinite;
    private long expireFreshTime;
}
