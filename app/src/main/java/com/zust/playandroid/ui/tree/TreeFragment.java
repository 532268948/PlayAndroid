package com.zust.playandroid.ui.tree;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.R;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.contract.tree.TreeContract;
import com.zust.playandroid.presenter.tree.TreePresenter;

public class TreeFragment extends BaseFragment<TreeContract.View,TreePresenter<TreeContract.View>> implements TreeContract.View {

    private RecyclerView mRecyclerView;

    @Override
    protected TreePresenter<TreeContract.View> createPresenter() {
        return new TreePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_tree,container,false);

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
