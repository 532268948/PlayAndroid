package com.zust.playandroid.presenter.homepage;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.BannerBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.homepage.HomeContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:38
 * 项 目： PlayAndroid
 * 描 述：
 */


public class HomePresenter<V extends HomeContract.View> extends BasePresenter<V> implements HomeContract.Presenter {

    private List<BannerBean> list = new ArrayList<>();

    @Override
    public void getBannerData() {
        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>())) {
            Log.e("Home1", cookie);
        }
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getBannerData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<BannerBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<BannerBean>> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            list.clear();

                            list.addAll(responseWrapper.getData());
//                            view.get().showBannerImage(list);
                            getArticleData();
                        } else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void getArticleData() {
        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>())) {
            Log.e("Home2", cookie);
        }
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getHomeArticleData(0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().showArticleList(responseWrapper.getData(), list);
                        } else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>()))
                            Log.e("Home3", cookie);
                    }
                }));
    }

    @Override
    public void refreshArticle() {
//        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>())) {
//            Log.e("Home2", cookie);
//        }
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getHomeArticleData(0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().refreshSuccess();
                            view.get().refreshArticle(responseWrapper.getData());
                        } else {
                            view.get().refreshFalse();
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
//                        for (String cookie : PlayAndroidPreference.getInstance(context.get()).getCookie(new HashSet<String>()))
//                            Log.e("Home3", cookie);
                    }
                }));
    }

    @Override
    public void loadMoreArticle() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getHomeArticleData(view.get().getPage()+1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().loadSuccess();
                            view.get().loadMoreArticle(responseWrapper.getData());
                        } else {
                            view.get().loadFalse();
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

}
