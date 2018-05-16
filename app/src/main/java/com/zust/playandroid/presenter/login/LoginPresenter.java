package com.zust.playandroid.presenter.login;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.login.LoginContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 10:32
 * 项 目： PlayAndroid
 * 描 述：
 */


public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter {


    @Override
    public void login() {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getLoginData(view.get().getUserName(),view.get().getPassword())
        .observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<LoginBean>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<LoginBean> loginBeanResponseWrapper) {
                if (loginBeanResponseWrapper.getErrorCode()==0){
                    view.get().loginSuccess();
                    gotoGuideActivity();
                }else {
                    view.get().loginFalse();
                    view.get().showMessage(loginBeanResponseWrapper.getErrorMsg());
                }
            }
        }));
    }

    @Override
    public void gotoGuideActivity() {
//        Log.e("Login",PlayAndroidPreference.getInstance(context.get()).getCookie());
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                try {
                    PlayAndroidPreference.getInstance(context.get()).setAccount(view.get().getUserName());
                    PlayAndroidPreference.getInstance(context.get()).setPassword(view.get().getPassword());
                    PlayAndroidPreference.getInstance(context.get()).setRemember(true);
                    Log.e("LoginActivity",PlayAndroidPreference.getInstance(context.get()).getRemember()+" ");
                    if (PlayAndroidPreference.getInstance(context.get()).getFirst()){
                        PlayAndroidPreference.getInstance(context.get()).setFirst(false);
                        emitter.onNext(1);
                    }else {
                        emitter.onNext(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onNext(3);
                }
            }
        }).delay(2, TimeUnit.SECONDS)
        .subscribeWith(new BaseObserver<Integer>(view.get()){
            @Override
            public void onNext(Integer integer) {
                if (integer==1){
                    view.get().gotoGuideActivity();
                }else if (integer==2){
                    view.get().gotoMainActivity();
                }else if (integer==3){
                    view.get().showMessage("未知错误");
                }
            }
        }));
    }

    @Override
    public void gotoMainActivity() {

    }

    @Override
    public void gotoRegisterActivity() {
        view.get().gotoRegisterActivity();
    }
}
