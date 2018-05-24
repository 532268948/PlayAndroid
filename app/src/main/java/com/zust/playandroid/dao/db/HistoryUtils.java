package com.zust.playandroid.dao.db;

import android.content.Context;
import android.util.Log;

import com.ping.greendao.gen.HistoryDao;
import com.zust.playandroid.bean.db.History;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/21
 * 时 间： 10:50
 * 项 目： PlayAndroid
 * 描 述：
 */


public class HistoryUtils {
    private static final String TAG = HistoryUtils.class.getSimpleName();
    private DaoManager mManager;

    public HistoryUtils(Context context){
        mManager = DaoManager.getInstance(context);
    }

    public boolean insertHistory(History history){
        boolean flag = false;
        List<History> historyList=queryMeiziByContent(history.getContent());
        if (historyList.size()!=0){
            historyList.get(0).setTime(history.getTime());
            try {
                mManager.getDaoSession().getHistoryDao().update(historyList.get(0));
                flag=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            flag = mManager.getDaoSession().getHistoryDao().insert(history) == -1 ? false : true;
            Log.e(TAG, "insert History :" + flag + "-->" + history.toString());
        }

        return flag;
    }
    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(History.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<History> queryAllHistory(){
        return mManager.getDaoSession().queryBuilder(History.class).orderDesc(HistoryDao.Properties.Time).list();
    }
    public List<History> queryMeiziByContent(String content){
        Log.e(TAG,"query:"+content);
        return mManager.getDaoSession().queryBuilder(History.class).where(HistoryDao.Properties.Content.eq(content)).list();
    }
}
