package com.zust.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zust.playandroid.R;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.ui.Project.list.detail.ProjectDetailActivity;
import com.zust.playandroid.ui.homepage.detial.ArticleDetialActivity;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/19
 * 时 间： 14:07
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private Context context;
    private List<ArticleBean> articleBeanList;

    public ProjectListAdapter(Context context, List<ArticleBean> articleBeanList){
        this.context=context;
        this.articleBeanList=articleBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(articleBeanList.get(position).getEnvelopePic()).into(holder.mImageView);
        holder.mTitleTextView.setText(articleBeanList.get(position).getTitle());
        holder.mContentTextView.setText(articleBeanList.get(position).getDesc());
        holder.mTimeTextView.setText(articleBeanList.get(position).getNiceDate());
        holder.mAuthorTextView.setText(articleBeanList.get(position).getAuthor());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ArticleDetialActivity.class);
                intent.putExtra("id",articleBeanList.get(position).getId());
                intent.putExtra("title",articleBeanList.get(position).getTitle());
                intent.putExtra("link",articleBeanList.get(position).getLink());
                intent.putExtra("isCollect",articleBeanList.get(position).isCollect());
                intent.putExtra("author",articleBeanList.get(position).getAuthor());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mImageView;
        TextView mTitleTextView;
        TextView mContentTextView;
        TextView mTimeTextView;
        TextView mAuthorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.card_view);
            mImageView=(ImageView)itemView.findViewById(R.id.picture);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title);
            mContentTextView=(TextView)itemView.findViewById(R.id.content);
            mTimeTextView=(TextView)itemView.findViewById(R.id.time);
            mAuthorTextView=(TextView)itemView.findViewById(R.id.author);
        }
    }
}
