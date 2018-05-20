package com.zust.playandroid.ui.Project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
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
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.ProjectClassifyBean;
import com.zust.playandroid.contract.project.ProjectContract;
import com.zust.playandroid.presenter.Project.ProjectPresenter;
import com.zust.playandroid.ui.Project.list.ProjectListFragment;
import com.zust.playandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment<ProjectContract.View, ProjectPresenter<ProjectContract.View>> implements ProjectContract.View {

    private static final String TAG = "ProjectFragment";

    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SmartRefreshLayout mRefreshLayout;

    private ContentPager mAdapter;

    private List<ProjectListFragment> projectListFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected ProjectPresenter<ProjectContract.View> createPresenter() {
        return new ProjectPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);

        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.bottom_nav_item_gray), ContextCompat.getColor(getActivity(), R.color.button_progress_blue));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.button_progress_blue));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, mTabLayout.getSelectedTabPosition() + "");
                if (projectListFragmentList != null) {
                    projectListFragmentList.get(mTabLayout.getSelectedTabPosition()).refreshData();
                    mRefreshLayout.finishRefresh(2000, true);
                }

            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (projectListFragmentList != null) {
                    projectListFragmentList.get(mTabLayout.getSelectedTabPosition()).loadMoreData();
                    mRefreshLayout.finishLoadMore(2000);
                }
            }
        });

        return view;
    }

    @Override
    public void initData() {
        presenter.getProjectClassifyData();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.shortToast(message);
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showProjectClassifyData(List<ProjectClassifyBean> projectClassifyBeanList) {
        for (ProjectClassifyBean bean : projectClassifyBeanList) {
            this.projectListFragmentList.add(new ProjectListFragment(bean.getId()));
            this.mTitleList.add(bean.getName());
        }
        mAdapter = new ContentPager(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    class ContentPager extends FragmentPagerAdapter {
        public ContentPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return projectListFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return projectListFragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

    }
}
