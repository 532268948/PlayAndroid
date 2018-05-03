package com.zust.playandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/23
 * 时 间： 11:44
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ToastUtil {
    private static Context context;
    public ToastUtil(Context context){
        this.context=context.getApplicationContext();
    }
    public static void shortToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
