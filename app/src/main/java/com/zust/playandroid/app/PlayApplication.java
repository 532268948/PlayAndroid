package com.zust.playandroid.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/18
 * 时 间： 14:43
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
