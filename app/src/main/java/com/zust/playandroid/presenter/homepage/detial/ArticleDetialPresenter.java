package com.zust.playandroid.presenter.homepage.detial;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.homepage.detial.ArticleDetialContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/7
 * 时 间： 15:19
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ArticleDetialPresenter<V extends ArticleDetialContract.View> extends BasePresenter<V> implements ArticleDetialContract.Presenter {


    @Override
    public void addCollect() {
        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>()))
            Log.e("Detail", "addCollect()" + cookie);
        Log.e("Detail",view.get().getId()+"");
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).addCollect(view.get().getId())
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().showMessage("收藏成功");
                            Log.e("Detail", "收藏成功");
                        } else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                            Log.e("Detail", responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void cancelCollect() {
        Log.e("Detail", "cancelCollect()");
    }
}
