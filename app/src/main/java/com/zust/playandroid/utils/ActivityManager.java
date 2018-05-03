package com.zust.playandroid.utils;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/23
 * 时 间： 16:06
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ActivityManager {
    private static ActivityManager activityManager;

    public synchronized static ActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    private Set<Activity> allActivities;

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void removeWhenLogin(){
        if (allActivities!=null){
            synchronized (allActivities){
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
