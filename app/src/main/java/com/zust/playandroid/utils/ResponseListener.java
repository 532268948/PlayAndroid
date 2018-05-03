package com.zust.playandroid.utils;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 14:36
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface ResponseListener<T> {
    void onResponse(T t);
    void onFailer(String message);
}
