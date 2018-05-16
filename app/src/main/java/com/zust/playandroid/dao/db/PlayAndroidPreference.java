package com.zust.playandroid.dao.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.zust.playandroid.app.PlayApplication;

import java.util.HashSet;
import java.util.Set;

import okhttp3.Cookie;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/1
 * 时 间： 20:25
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayAndroidPreference implements PreferenceHelper{

    private volatile static PlayAndroidPreference instance=null;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    HashSet<String> siteno = new HashSet<String>();

    private PlayAndroidPreference(Context context){
        sp=context.getSharedPreferences("playandroid", Activity.MODE_PRIVATE);
        editor=sp.edit();
    }

    public static PlayAndroidPreference getInstance(Context context){
        if (instance==null){
            synchronized (PlayAndroidPreference.class){
                if (instance==null){
                    instance=new PlayAndroidPreference(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    @Override
    public void setAccount(String username) {
        editor.putString("username",username).commit();
    }

    @Override
    public String getAccount() {
        return sp.getString("username","");
    }

    @Override
    public void setPassword(String password) {
        editor.putString("password",password).commit();
    }

    @Override
    public String getPassword() {
        return sp.getString("password","");
    }

    @Override
    public void setFirst(boolean is) {
        editor.putBoolean("first",is).commit();
    }

    @Override
    public Boolean getFirst() {
        return sp.getBoolean("first",true);
    }

    @Override
    public void setRemember(boolean remember) {
        editor.putBoolean("remember",remember).commit();
    }

    @Override
    public Boolean getRemember() {
        return sp.getBoolean("remember",false);
    }

    @Override
    public void setCookie(String cookie) {
        editor.putString("cookie",cookie).commit();
    }

    @Override
    public String getCookie() {
        return sp.getString("cookie","");
    }

    @Override
    public void setCookie(HashSet<String> cookies){
        editor.putStringSet("cookies",cookies).commit();
    }

    @Override
    public HashSet<String> getCookie(HashSet<String> cookie) {
        return (HashSet<String>) sp.getStringSet("cookies",cookie);
    }
}
