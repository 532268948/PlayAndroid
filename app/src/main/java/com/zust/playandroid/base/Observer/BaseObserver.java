package com.zust.playandroid.base.Observer;

import android.view.View;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.utils.ToastUtil;

import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.ResourceObserver;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/1
 * 时 间： 12:26
 * 项 目： PlayAndroid
 * 描 述：
 */


public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private BaseView mView;

    public BaseObserver(BaseView view){
        this.mView=view;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof TimeoutException){
            mView.showMessage("请求超时");
        }else if (e instanceof ConnectException){
            mView.showMessage("连接异常");
        }
    }

    @Override
    public void onComplete() {

    }
}
