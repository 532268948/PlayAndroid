package com.zust.playandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.base.view.BaseView;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:12
 * 项 目： PlayAndroid
 * 描 述：
 */


public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements BaseView{

    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isLoad = false;
    public T presenter;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        presenter = createPresenter();
        presenter.attachView((V) this,mContext);
        setHasOptionsMenu(true);
    }

    protected abstract T createPresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater,container);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isPrepared = true;
        lazyLoad();
    }


    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        if (!isLoad) {
            //loadAnimation();
            initData();
            isLoad=true;
        }
    }

    protected void onInvisible() {

    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    //public abstract void loadAnimation();

}
