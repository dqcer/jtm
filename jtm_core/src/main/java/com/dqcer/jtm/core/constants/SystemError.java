package com.dqcer.jtm.core.constants;

/**
 * @Author: dongQin
 * @Date: 2018/11/9 13:08
 * @Description: 系统异常
 */

public enum SystemError {

    /**单点登录系统*/
    INVALID_AUTH_INFO   ("000001","认证失败,请重新输入"),

    ACCOUNT_STOP ("000002","该用户已停用");

    private String code;

    private String message;

    SystemError(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
