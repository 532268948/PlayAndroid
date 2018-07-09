package com.zust.playandroid.ui.tree;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.KnowledgeTreeBean;
import com.zust.playandroid.contract.tree.Tree2Contract;
import com.zust.playandroid.presenter.tree.Tree2Presenter;

import java.util.ArrayList;
import java.util.List;

public class Tree2Activity extends BaseActivity<Tree2Contract.View,Tree2Presenter<Tree2Contract.View>> implements Tree2Contract.View {

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private ContentPager mAdapter;

    private List<Tree2Fragment> fragmentList;
    private List<String> titleList;
    private List<KnowledgeTreeBean> knowledgeTreeBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree2);
        getWindow().setStatusBarColor(getResources().getColor(R.color.button_progress_blue));
        Intent intent=getIntent();
        knowledgeTreeBeanList=(List<KnowledgeTreeBean>)intent.getSerializableExtra("knowledge");
        String title=intent.getStringExtra("title");

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout=(TabLayout)findViewById(R.id.tab_layout);
        mViewPager=(ViewPager)findViewById(R.id.view_pager);

        if (knowledgeTreeBeanList.size()<=4){
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.bottom_nav_item_gray), ContextCompat.getColor(this, R.color.button_progress_blue));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.button_progress_blue));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
        showData();
    }

    @Override
    protected Tree2Presenter<Tree2Contract.View> createPresenter() {
        return new Tree2Presenter<>();
    }


    @Override
    public void showData() {
        this.fragmentList=new ArrayList<>(this.knowledgeTreeBeanList.size());
        this.titleList=new ArrayList<>(this.knowledgeTreeBeanList.size());
        for(KnowledgeTreeBean knowledgeTreeBean:this.knowledgeTreeBeanList){
            this.fragmentList.add(new Tree2Fragment(knowledgeTreeBean.getId()));
            this.titleList.add(knowledgeTreeBean.getName());
        }
        mAdapter=new ContentPager(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    class ContentPager extends FragmentPagerAdapter {
        public ContentPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

    }
}
