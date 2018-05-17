package com.zust.playandroid.presenter.tree;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.KnowledgeTreeBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.tree.TreeContract;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:41
 * 项 目： PlayAndroid
 * 描 述：
 */


public class TreePresenter<V extends TreeContract.View> extends BasePresenter<V> implements TreeContract.Presenter {
    @Override
    public void getData() {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getKnowledgeTreeData()
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<List<KnowledgeTreeBean>>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<List<KnowledgeTreeBean>> responseWrapper) {
                if (responseWrapper.getErrorCode()==0){
                    view.get().showData(responseWrapper.getData());
                }else {
                    view.get().showMessage(responseWrapper.getErrorMsg());
                }
            }
        }));
    }
}
