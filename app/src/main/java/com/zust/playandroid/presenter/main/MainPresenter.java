package com.zust.playandroid.presenter.main;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.contract.main.MainContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.ui.main.MainActivity;
import com.zust.playandroid.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/3
 * 时 间： 13:10
 * 项 目： PlayAndroid
 * 描 述：
 */


public class MainPresenter<V extends MainContract.View> extends BasePresenter<V> implements MainContract.Presenter {
    @Override
    public void titleChangeAnimation() {
//        if (!view.get().isAnimating()){
//            view.get().cancelAnimation();
//        }
        view.get().cancelAnimation();
        addDiaposable(Observable.intervalRange(1, 180, 0, 5, TimeUnit.MILLISECONDS)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Long>(view.get()) {
                    @Override
                    public void onNext(Long aLong) {
                        if (aLong < 90) {
                            view.get().titleChangeAnimation(aLong);
                        } else if (aLong == 90) {
                            view.get().titleChangeAnimation(aLong);
                            view.get().setTitle();
                        } else {
                            view.get().titleChangeAnimation(aLong - 180);
                        }
                    }
                }));
//        view.get().showMessage("hah");
    }

    @Override
    public void Logout() {
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.e("MainPresenter","Logout");
                PlayAndroidPreference.getInstance(context.get()).setAccount("");
                PlayAndroidPreference.getInstance(context.get()).setPassword("");
                PlayAndroidPreference.getInstance(context.get()).setRemember(false);
//                Log.e("MainPresenter",PlayAndroidPreference.getInstance(context.get()).getRemember()+" ");
//                ToastUtil.shortToast(PlayAndroidPreference.getInstance(context.get()).getRemember()+" ");
                emitter.onNext(0);
            }
        }).subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<Integer>(view.get()) {
            @Override
            public void onNext(Integer integer) {
                view.get().Logout();
            }
        }));
    }
}
