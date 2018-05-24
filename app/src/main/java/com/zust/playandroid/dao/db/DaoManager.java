package com.zust.playandroid.dao.db;

import android.content.Context;

import com.ping.greendao.gen.DaoMaster;
import com.ping.greendao.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/21
 * 时 间： 10:52
 * 项 目： PlayAndroid
 * 描 述：
 */


public class DaoManager {
    private static final String DB_NAME="playandroid";
    private Context context;
    private volatile static DaoManager instance;

    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    public DaoManager(Context context){
        this.context=context;
    }

    public static DaoManager getInstance(Context context){
        if (instance==null){
            synchronized (DaoManager.class){
                if (instance==null){
                    instance=new DaoManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
    /**
     * 判断是否有存在数据库，如果没有则创建
     * @return
     */
    public DaoMaster getDaoMaster(){
        if(sDaoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     * @return
     */
    public DaoSession getDaoSession(){
        if(sDaoSession == null){
            if(sDaoMaster == null){
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}
