package com.dqcer.jtm.sso.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class PubUserEntity extends JSONObject implements Serializable {

    private static final long serialVersionUID = 7369216982738598107L;

    private Long id;

    private String userName;

    private String userEmail;

    private String password;

    private String salt;

    private String state;

    private Date createTime;

    private Date updateTime;

    private Date invalidTime;

    private Date invalidUser;

    private String invalidReason;

    private String invalidReasonDesc;
}
