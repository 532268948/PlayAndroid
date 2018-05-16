package com.zust.playandroid.bean;

import java.io.Serializable;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 13:47
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ResponseWrapper<T> implements Serializable{
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    /**
     * 0：成功，1：失败
     */
    private int errorCode;

    private String errorMsg;

    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
