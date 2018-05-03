package com.zust.playandroid.bean;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 13:48
 * 项 目： PlayAndroid
 * 描 述：
 */


public class LoginBean {
    String username;
    String password;

    public LoginBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
