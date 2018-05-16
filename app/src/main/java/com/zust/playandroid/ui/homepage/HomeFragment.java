package com.zust.playandroid.ui.homepage;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zust.playandroid.R;
import com.zust.playandroid.adapter.HomeArticleAdapter;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.BannerBean;
import com.zust.playandroid.contract.homepage.HomeContract;
import com.zust.playandroid.presenter.homepage.HomePresenter;

import java.util.List;

public class HomeFragment extends BaseFragment<HomeContract.View, HomePresenter<HomeContract.View>> implements
        HomeContract.View{

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    private HomeArticleAdapter mAdapter;

    private ArticleListBean articleListBean;

    private Integer mPage=0;

    @Override
    protected HomePresenter<HomeContract.View> createPresenter() {
        return new HomePresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSmartRefreshLayout=(SmartRefreshLayout)view.findViewById(R.id.refresh_layout);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.refreshArticle();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMoreArticle();
            }
        });
        return view;
    }


    @Override
    public void initData() {
        mSmartRefreshLayout.autoRefresh(0,800,1);
        presenter.getBannerData();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showArticleList(ArticleListBean articleListBean, List<BannerBean> imagePathList) {
        this.articleListBean = articleListBean;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeArticleAdapter(this.articleListBean.getDatas(), imagePathList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        View mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.home_banner, mRecyclerView, false);
        mAdapter.setHeaderView(mHeaderView);
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void refreshArticle(ArticleListBean articleListBean) {
        this.articleListBean=articleListBean;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshSuccess() {
        mSmartRefreshLayout.finishRefresh(1000,true);
    }

    @Override
    public void refreshFalse() {
        mSmartRefreshLayout.finishRefresh(1000,true);
    }

    @Override
    public void loadMoreArticle(ArticleListBean articleListBean){
        this.articleListBean.getDatas().addAll(articleListBean.getDatas());
        Log.e("loadMoreArticle",this.articleListBean.getDatas().size()+"");
        mAdapter.notifyDataSetChanged();
        mPage++;
    }

    @Override
    public void loadSuccess() {
        mSmartRefreshLayout.finishLoadMore(1000);
    }

    @Override
    public void loadFalse() {
        mSmartRefreshLayout.finishLoadMore(1000);
    }

    @Override
    public void onStart() {
        super.onStart();
//        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mBanner.stopAutoPlay();
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
