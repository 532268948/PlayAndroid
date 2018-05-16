package com.zust.playandroid.presenter.main;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.contract.main.MainContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
                        if (aLong<90){
                            view.get().titleChangeAnimation(aLong);
                        }else if (aLong==90){
                            view.get().titleChangeAnimation(aLong);
                            view.get().setTitle();
                        }else {
                            view.get().titleChangeAnimation(aLong-180   );
                        }
                    }
                }));
//        view.get().showMessage("hah");
    }
}
