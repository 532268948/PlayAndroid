package com.zust.playandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 15:11
 * 项 目： PlayAndroid
 * 描 述：
 */


public class CacheUtil {
    public static void putBoolean(Context context, String key, boolean values) {
        SharedPreferences sp=context.getSharedPreferences("zero",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,values).commit();
    }
    //获取布尔值
    public static boolean getBoolean(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences("zero",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
    //存储字符串
    public static void putString(Context context, String key, String values) {
        SharedPreferences sp=context.getSharedPreferences("zero",Context.MODE_PRIVATE);
        sp.edit().putString(key,values).commit();
    }
    //获取字符串
    public static String getString(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences("zero",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
