package com.zust.playandroid.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zust.playandroid.utils.ToastUtil;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/18
 * 时 间： 14:43
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayApplication extends Application {

    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        ToastUtil toastUtil=new ToastUtil(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        PlayApplication leakApplication = (PlayApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }
}
