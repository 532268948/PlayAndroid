package com.zust.playandroid.presenter.Project;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ProjectClassifyBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.project.ProjectContract;
import com.zust.playandroid.contract.project.list.ProjectListContract;
import com.zust.playandroid.utils.PlayAndroidService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:52
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ProjectPresenter<V extends ProjectContract.View> extends BasePresenter<V> implements ProjectContract.Presenter {
    @Override
    public void getProjectClassifyData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getProjectClassifyData()
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<List<ProjectClassifyBean>>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<List<ProjectClassifyBean>> responseWrapper) {
                if (responseWrapper.getErrorCode()==0){
                    view.get().showProjectClassifyData(responseWrapper.getData());
                }else {
                    view.get().showMessage(responseWrapper.getErrorMsg());
                }
            }
        }));
    }
}
