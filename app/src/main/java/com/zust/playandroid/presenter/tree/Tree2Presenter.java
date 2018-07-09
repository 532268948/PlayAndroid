package com.zust.playandroid.presenter.tree;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.contract.tree.Tree2Contract;
import com.zust.playandroid.utils.PlayAndroidService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Tree2Presenter<V extends Tree2Contract.View> extends BasePresenter<V> implements Tree2Contract.Presenter {
    @Override
    public void getData() {

    }
}
