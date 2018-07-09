package com.zust.playandroid.ui.tree;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zust.playandroid.R;
import com.zust.playandroid.adapter.TreeListAdapter;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.contract.tree.TreeDetailContract;
import com.zust.playandroid.presenter.tree.TreeDetailPresenter;

import java.util.List;

@SuppressLint("ValidFragment")
public class Tree2Fragment extends BaseFragment<TreeDetailContract.View, TreeDetailPresenter<TreeDetailContract.View>> implements TreeDetailContract.View {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private TreeListAdapter mAdapter;

    private List<ArticleBean> articleBeanList;

    private int id;
    private int mPage=0;

    public Tree2Fragment(int id) {
        this.id = id;
    }

    @Override
    protected TreeDetailPresenter<TreeDetailContract.View> createPresenter() {
        return new TreeDetailPresenter<>();
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_tree2, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        mRefreshLayout=(SmartRefreshLayout)view.findViewById(R.id.refresh_layout);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.Refresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.LoadMore();
            }
        });
        return view;
    }

    @Override
    public void initData() {
        presenter.getData();
    }

    @Override
    public void showData(List<ArticleBean> articleBeanList) {
        this.articleBeanList=articleBeanList;
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new TreeListAdapter(getContext(),this.articleBeanList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getTreeId() {
        return id;
    }

    @Override
    public void getRefreshData(List<ArticleBean> articleBeanList) {
        this.articleBeanList.clear();
        this.articleBeanList.addAll(articleBeanList);
        mAdapter.notifyDataSetChanged();
        mPage=0;
        mRefreshLayout.finishRefresh(2000,true);
    }

    @Override
    public void getLoadData(List<ArticleBean> articleBeanList) {
        this.articleBeanList.addAll(articleBeanList);
        mAdapter.notifyDataSetChanged();
        mPage++;
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public int getPage() {
        return mPage;
    }
}
