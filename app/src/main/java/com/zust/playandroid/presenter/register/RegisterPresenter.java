package com.zust.playandroid.presenter.register;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.register.RegisterContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.utils.PlayAndroidService;
import com.zust.playandroid.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/23
 * 时 间： 15:11
 * 项 目： PlayAndroid
 * 描 述：
 */


public class RegisterPresenter<V extends RegisterContract.View> extends BasePresenter<V> implements RegisterContract.Presenter {

    @Override
    public void getRegisterData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get())
                .getRegisterData(view.get().getName(), view.get().getPassword(), view.get().getRePassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<LoginBean>>(view.get()){
                    @Override
                    public void onNext(ResponseWrapper<LoginBean> loginBeanResponseWrapper) {
                        if (loginBeanResponseWrapper.getErrorCode()!=0){
                            view.get().FalseAnimation();
                            view.get().showMessage(loginBeanResponseWrapper.getErrorMsg()+PlayAndroidPreference.getInstance(context.get()).getFirst());
                        }else {
                            view.get().SuccessAnimation();
                            view.get().showMessage("注册成功！");
                            savePersonInformation(loginBeanResponseWrapper.getData().getUsername(),loginBeanResponseWrapper.getData().getPassword());
                        }
                    }
                }));
    }

    @Override
    public void savePersonInformation(final String username, final String password) {
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        try {
                            PlayAndroidPreference.getInstance(context.get()).setAccount(username);
                            PlayAndroidPreference.getInstance(context.get()).setPassword(password);
                            PlayAndroidPreference.getInstance(context.get()).setRemember(true);
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
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<Integer>(view.get()) {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer==1){
                            view.get().gotoGuideActivity();
                        }else if(integer==2){
                            view.get().gotoMainActivity();
                        }else if (integer==3){
                            view.get().showMessage("存储登录信息时出现未知错误");
                        }
                    }
                })
        );
    }
}
