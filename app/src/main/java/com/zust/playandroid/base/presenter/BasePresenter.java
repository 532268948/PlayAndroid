package com.zust.playandroid.base.presenter;

import android.content.Context;

import com.zust.playandroid.base.view.BaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 9:50
 * 项 目： PlayAndroid
 * 描 述：
 */


public class BasePresenter<V> {

    /**
     * view的弱引用
     *  @param view
     */
    protected WeakReference<V> view;

    protected WeakReference<Context> context;

    private CompositeDisposable compositeDisposable;

    /**
     * disposable管理
     * @param disposable
     */
    protected void addDiaposable(Disposable disposable){
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * view绑定
     * @param view
     */
    public void attachView(V view,Context context){
        this.view=new WeakReference<V>(view);
        this.context=new WeakReference<>(context);
//        this.context=new WeakReference<C>(context);
    }

    /**
     * view解绑
     */
    public void detachView(){
        this.view.clear();
        this.context.clear();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
//        this.context.clear();
    }
}
