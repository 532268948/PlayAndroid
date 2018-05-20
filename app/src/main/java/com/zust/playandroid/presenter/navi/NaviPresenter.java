package com.zust.playandroid.presenter.navi;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.NaviBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.navi.NaviContract;
import com.zust.playandroid.utils.PlayAndroidService;

import java.io.Serializable;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:47
 * 项 目： PlayAndroid
 * 描 述：
 */


public class NaviPresenter<V extends NaviContract.View> extends BasePresenter<V> implements NaviContract.Presenter {
    @Override
    public void getNaviData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getNaviData()
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<List<NaviBean>>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<List<NaviBean>> responseWrapper) {
                if (responseWrapper.getErrorCode()==0){
                    view.get().showData(responseWrapper.getData());
                }else {
                    view.get().showMessage(responseWrapper.getErrorMsg());
                }
            }
        }));
    }
}
