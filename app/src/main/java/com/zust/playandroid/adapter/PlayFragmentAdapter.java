package com.zust.playandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/5
 * 时 间： 12:20
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public PlayFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
