package com.zust.playandroid.ui.Project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.R;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.contract.project.ProjectContract;
import com.zust.playandroid.presenter.Project.ProjectPresenter;

public class ProjectFragment extends BaseFragment<ProjectContract.View,ProjectPresenter<ProjectContract.View>> implements ProjectContract.View {

    private RecyclerView mRecyclerView;
    @Override
    protected ProjectPresenter<ProjectContract.View> createPresenter() {
        return new ProjectPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_project,container,false);

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
