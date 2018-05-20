package com.zust.playandroid.presenter.Project.list;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ProjectListBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.project.list.ProjectListContract;
import com.zust.playandroid.utils.PlayAndroidService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/19
 * 时 间： 12:41
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ProjectListPresenter<V extends ProjectListContract.View> extends BasePresenter<V> implements ProjectListContract.Presenter {

    private boolean isSuccess = false;

    @Override
    public void getProjectListData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getProjectListData(1, view.get().getListId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ProjectListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ProjectListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().showProjectListData(responseWrapper.getData().getDatas());
                        } else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void getRefreshData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getProjectListData(1, view.get().getListId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ProjectListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ProjectListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().refreshSuccess(responseWrapper.getData().getDatas());
                            view.get().showMessage(responseWrapper.getErrorMsg());

                        }
                    }
                }));
    }

    @Override
    public void getLoadMoreData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getProjectListData(view.get().getCurrentPage() + 1, view.get().getListId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ProjectListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ProjectListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode() == 0) {
                            view.get().loadMoreSuccess(responseWrapper.getData().getDatas());
                            view.get().setCurrentPage(view.get().getCurrentPage()+1);
                        } else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }
}
