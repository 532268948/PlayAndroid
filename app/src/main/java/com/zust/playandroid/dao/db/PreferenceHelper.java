package com.zust.playandroid.dao.db;

import java.util.HashSet;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/1
 * 时 间： 20:23
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface PreferenceHelper {
    void setAccount(String username);
    String getAccount();
    void setPassword(String password);
    String getPassword();
    void setFirst(boolean is);
    Boolean getFirst();
    void setRemember(boolean remember);
    Boolean getRemember();
    void setCookie(String cookie);
    String getCookie();
    void setCookie(HashSet<String> cookies);
    HashSet<String> getCookie(HashSet<String> cookie);
}
