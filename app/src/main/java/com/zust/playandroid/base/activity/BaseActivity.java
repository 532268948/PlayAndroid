package com.zust.playandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.utils.ActivityManager;
import com.zust.playandroid.utils.ToastUtil;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 9:37
 * 项 目： PlayAndroid
 * 描 述：
 */


public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity implements BaseView{

    public T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        presenter=createPresenter();
        presenter.attachView((V) this,this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
        presenter.detachView();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.shortToast(message);
    }
}
