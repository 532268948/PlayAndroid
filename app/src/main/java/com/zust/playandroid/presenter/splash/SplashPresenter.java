package com.zust.playandroid.presenter.splash;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.splash.SplashContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/3
 * 时 间： 15:26
 * 项 目： PlayAndroid
 * 描 述：
 */


public class SplashPresenter<V extends SplashContract.View> extends BasePresenter<V> implements SplashContract.Presenter {
    @Override
    public void init() {
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.e("SplashPresenter",PlayAndroidPreference.getInstance(context.get()).getRemember()+" ");
                if (PlayAndroidPreference.getInstance(context.get()).getRemember()) {
                    emitter.onNext(1);
                } else {
                    emitter.onNext(2);
                }
            }
        }).delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Integer>(view.get()) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer==1){
                            getInformation();
                        }else if (integer==2){
                            view.get().gotoLoginActivity();
                        }
                    }
                }));
    }

    @Override
    public void getInformation() {
        addDiaposable(Observable.create(new ObservableOnSubscribe<LoginBean>() {
            @Override
            public void subscribe(ObservableEmitter<LoginBean> emitter) throws Exception {
                LoginBean loginBean=new LoginBean(PlayAndroidPreference.getInstance(context.get()).getAccount(),
                        PlayAndroidPreference.getInstance(context.get()).getPassword());
                emitter.onNext(loginBean);

            }
        }).subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<LoginBean>(view.get()){
            @Override
            public void onNext(LoginBean loginBean) {
                login(loginBean);
            }
        }));
    }

    @Override
    public void login(LoginBean loginBean) {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getLoginData(loginBean.getUsername(),loginBean.getPassword())
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<LoginBean>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<LoginBean> responseWrapper) {
                view.get().gotoMainActivity();
            }
        }));
    }
}
