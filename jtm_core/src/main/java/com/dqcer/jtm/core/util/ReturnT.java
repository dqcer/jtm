package com.dqcer.jtm.core.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 17:16
 * @Description: 消息体
 */
@Data
public class ReturnT<T> implements Serializable {

    private static final long serialVersionUID = -7128772148766571252L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT<String>(null);
    public static final ReturnT<String> FAIL = new ReturnT<String>(FAIL_CODE, null);

    private int code;
    private String msg;
    private T data;

    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ReturnT(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

}
