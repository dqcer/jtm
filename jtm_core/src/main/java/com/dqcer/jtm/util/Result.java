package com.dqcer.jtm.util;

import java.io.Serializable;


/**
 * @Author: dongQin
 * @Date: 2018/10/27 15:09
 * @Description: 响应信息主体
 */

public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7247888468575179536L;

    /**
     * 信息内容
     */
    private T data;

    /**
     * 状态
     */
    private String status;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    public Result() {
        this.status = Status.SUCCESS.code();
    }


    public Result(String mes) {
        this.status = Status.SUCCESS.code();
        this.data = (T) mes;
    }

    public Result(T data) {
        this.status = Status.SUCCESS.code();
        this.data = data;
    }

    public Result(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, Status.ERROR);
    }

    public Result(String errorCode, String errorMsg, Status status) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.status = status.code();
    }

    public Result error(String code, String message) {
        return new Result(code, message, Status.ERROR);
    }


    public enum Status {

        SUCCESS("OK"), ERROR("ERROR");

        private String code;

        Status(String code){
            this.code = code;
        }

        public String code(){
            return code;
        }

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
