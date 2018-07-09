package com.zust.playandroid.presenter.tree;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.tree.TreeDetailContract;
import com.zust.playandroid.utils.PlayAndroidService;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TreeDetailPresenter<V extends TreeDetailContract.View> extends BasePresenter<V> implements TreeDetailContract.Presenter {

    @Override
    public void getData() {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getKnowledgeHierarchyDetailData(0,view.get().getTreeId())
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                if (responseWrapper.getErrorCode()==0){
                    view.get().showData(responseWrapper.getData().getDatas());
                }else {
                    view.get().showMessage(responseWrapper.getErrorMsg());
                }
            }
        }));
    }

    @Override
    public void Refresh() {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getKnowledgeHierarchyDetailData(0,view.get().getTreeId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode()==0){
                            view.get().getRefreshData(responseWrapper.getData().getDatas());
                        }else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void LoadMore() {
        addDiaposable((Disposable)PlayAndroidService.getInstance(context.get()).getKnowledgeHierarchyDetailData(view.get().getPage(),view.get().getTreeId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper) {
                        if (responseWrapper.getErrorCode()==0){
                            view.get().getLoadData(responseWrapper.getData().getDatas());
                        }else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }
}
