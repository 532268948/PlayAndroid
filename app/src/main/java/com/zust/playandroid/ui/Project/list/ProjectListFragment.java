package com.zust.playandroid.ui.Project.list;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.R;
import com.zust.playandroid.adapter.ProjectListAdapter;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.contract.project.list.ProjectListContract;
import com.zust.playandroid.presenter.Project.list.ProjectListPresenter;
import com.zust.playandroid.utils.ToastUtil;

import java.util.List;

@SuppressLint("ValidFragment")
public class ProjectListFragment extends BaseFragment<ProjectListContract.View, ProjectListPresenter<ProjectListContract.View>> implements ProjectListContract.View {

    private static final String TAG = "ProjectListFragment";

    private RecyclerView mRecyclerView;
    private ProjectListAdapter mAdapter;

    private final int id;
    private int mPage = 1;

    private List<ArticleBean> articleBeanList;

    public ProjectListFragment(int id) {
        this.id = id;
    }

    @Override
    protected ProjectListPresenter<ProjectListContract.View> createPresenter() {
        return new ProjectListPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void initData() {
        Log.e(TAG, "initData" + id);
        presenter.getProjectListData();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.shortToast(message);
    }

    @Override
    public int getListId() {
        return id;
    }

    @Override
    public void showProjectListData(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
//        ToastUtil.shortToast(this.articleBeanList.size()+"");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProjectListAdapter(getActivity(), this.articleBeanList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void refreshData() {
        presenter.getRefreshData();
    }

    @Override
    public void loadMoreData() {
        presenter.getLoadMoreData();
    }

    @Override
    public boolean refreshSuccess(List<ArticleBean> articleBeanList) {
        this.articleBeanList.clear();
        this.articleBeanList.addAll(articleBeanList);
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean loadMoreSuccess(List<ArticleBean> articleBeanList) {
        this.articleBeanList.addAll(articleBeanList);
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public int getCurrentPage() {
        return mPage;
    }

    @Override
    public void setCurrentPage(int page) {
        this.mPage = page;
    }

    @Override
    public void jumpToTheTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

}
