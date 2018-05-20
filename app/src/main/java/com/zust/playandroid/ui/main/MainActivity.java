package com.zust.playandroid.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.adapter.PlayFragmentAdapter;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.main.MainContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.presenter.main.MainPresenter;
import com.zust.playandroid.ui.Project.ProjectFragment;
import com.zust.playandroid.ui.homepage.HomeFragment;
import com.zust.playandroid.ui.navi.NaviFragment;
import com.zust.playandroid.ui.search.SearchActivity;
import com.zust.playandroid.ui.tree.TreeFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends BaseActivity<MainContract.View, MainPresenter<MainContract.View>> implements
        MainContract.View,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, Toolbar.OnMenuItemClickListener,
        View.OnClickListener{

    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private TextView mTitleTextView;
    private FloatingActionButton mActionButton;

    private MenuItem menuItem;

    private String mTitle;

    private List<Fragment> fragmentList;
    private HomeFragment mHomeFragment;
    private TreeFragment mTreeFragment;
    private NaviFragment mNaviFragment;
    private ProjectFragment mProjectFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (String cookie : PlayAndroidPreference.getInstance(this).getCookie(new HashSet<String>()))
            Log.e("Main", cookie);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation_view);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mViewPager = (ViewPager) findViewById(R.id.view_page);
        mTitleTextView = (TextView) findViewById(R.id.title);
        mActionButton=(FloatingActionButton)findViewById(R.id.main_floating_action_btn);

        mTitle=getResources().getString(R.string.toolbar_title_home);

        mActionButton.setOnClickListener(this);

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.button_progress_blue));

        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        mToolbar.setOnMenuItemClickListener(this);

        fragmentList = new ArrayList<>();
        mHomeFragment=new HomeFragment();
        mTreeFragment=new TreeFragment();
        mNaviFragment=new NaviFragment();
        mProjectFragment=new ProjectFragment();
        fragmentList.add(mHomeFragment);
        fragmentList.add(mTreeFragment);
        fragmentList.add(mNaviFragment);
        fragmentList.add(mProjectFragment);
        PlayFragmentAdapter mAdapter = new PlayFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected MainPresenter<MainContract.View> createPresenter() {
        return new MainPresenter<>();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
//                mTitle=getResources().getString(R.string.toolbar_title_home);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tree:
//                mTitle=getResources().getString(R.string.toolbar_title_tree);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.navi:
//                mTitle=getResources().getString(R.string.toolbar_title_navi);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.project:
//                mTitle=getResources().getString(R.string.toolbar_title_project);
                mViewPager.setCurrentItem(3);
                break;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = mBottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
        switch (position) {
            case 0:
                mTitle = getResources().getString(R.string.toolbar_title_home);
                break;
            case 1:
                mTitle = getResources().getString(R.string.toolbar_title_tree);
                break;
            case 2:
                mTitle = getResources().getString(R.string.toolbar_title_navi);
                break;
            case 3:
                mTitle = getResources().getString(R.string.toolbar_title_project);
                break;
            default:
                break;
        }
        presenter.titleChangeAnimation();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.tree:

                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void titleChangeAnimation(float rotation) {
        mTitleTextView.setRotationY(rotation);
    }

    @Override
    public void setTitle() {
        mTitleTextView.setText(mTitle);
    }

    @Override
    public void searchShow() {

    }

    @Override
    public boolean isAnimating() {
        return mTitleTextView.getAnimation().hasEnded();
    }

    @Override
    public void cancelAnimation() {
        mTitleTextView.clearAnimation();
        mTitleTextView.setRotationY(0);
    }

    @Override
    public void onClick(View v) {
        if (mTitle.equals(getResources().getString(R.string.toolbar_title_home))){
            if (mHomeFragment!=null){
                mHomeFragment.jumpToTheTop();
            }
        }else if (mTitle.equals(getResources().getString(R.string.toolbar_title_tree))){
            if (mTreeFragment!=null){
                mTreeFragment.jumpToTheTop();
            }
        }else if(mTitle.equals(getResources().getString(R.string.toolbar_title_navi))){

        }else if (mTitle.equals(getResources().getString(R.string.toolbar_title_project))){

        }
    }
}
