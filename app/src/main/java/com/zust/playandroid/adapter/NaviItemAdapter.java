package com.zust.playandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zust.playandroid.bean.ArticleBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/18
 * 时 间： 14:26
 * 项 目： PlayAndroid
 * 描 述：
 */


public class NaviItemAdapter extends RecyclerView.Adapter<NaviItemAdapter.ViewHolder> {

    private Context context;
    private List<ArticleBean> articleBeanList;

    public NaviItemAdapter(Context context,List<ArticleBean> articleBeanList){
        this.context=context;
        this.articleBeanList=articleBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
