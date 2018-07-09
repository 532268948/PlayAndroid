package com.zust.playandroid.presenter.guide;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.contract.guide.GuideContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GuidePresenter<V extends GuideContract.View> extends BasePresenter<V> implements GuideContract.Presennter {
    @Override
    public void init() {
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                PlayAndroidPreference.getInstance(context.get()).setFirst(false);
                emitter.onNext(1);
            }
        }).subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<Integer>(view.get()) {
            @Override
            public void onNext(Integer integer) {
                if (integer==1){
                    view.get().gotoMainActivity();
                }else {
                    view.get().showMessage("出现未知错误");
                }
            }
        }));
    }
}
