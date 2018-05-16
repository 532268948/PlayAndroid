package com.zust.playandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/6
 * 时 间： 13:22
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayViewAdapter extends PagerAdapter {

    private List<View> viewList;

    public PlayViewAdapter(List<View> viewList){
        this.viewList=viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }



}
