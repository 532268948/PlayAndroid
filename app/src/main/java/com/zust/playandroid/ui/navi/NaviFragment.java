package com.zust.playandroid.ui.navi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.R;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.contract.navi.NaviContract;
import com.zust.playandroid.presenter.navi.NaviPresenter;

public class NaviFragment extends BaseFragment<NaviContract.View,NaviPresenter<NaviContract.View>> implements NaviContract.View {

    private RecyclerView mRecyclerView;

    @Override
    protected NaviPresenter<NaviContract.View> createPresenter() {
        return new NaviPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_navi,container,false);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void showMessage(String message) {

    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
