package com.zust.playandroid.ui.tree;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zust.playandroid.R;
import com.zust.playandroid.adapter.TreeAdapter;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.KnowledgeTreeBean;
import com.zust.playandroid.contract.tree.TreeContract;
import com.zust.playandroid.presenter.tree.TreePresenter;

import java.util.List;

public class TreeFragment extends BaseFragment<TreeContract.View,TreePresenter<TreeContract.View>> implements TreeContract.View {

    private static String TAG="TreeFragment";

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private TreeAdapter mAdapter;

    private List<KnowledgeTreeBean> knowledgeTreeBeanList;

    @Override
    protected TreePresenter<TreeContract.View> createPresenter() {
        return new TreePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_tree,container,false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        mRefreshLayout=(SmartRefreshLayout)view.findViewById(R.id.refresh_layout);

        return view;
    }

    @Override
    public void initData() {
        presenter.getData();
    }

    @Override
    public void showMessage(String message) {

    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showData(List<KnowledgeTreeBean> knowledgeTreeBeanList) {
        Log.e(TAG,"showData()"+knowledgeTreeBeanList.size());
        this.knowledgeTreeBeanList=knowledgeTreeBeanList;
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new TreeAdapter(getContext(),this.knowledgeTreeBeanList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
