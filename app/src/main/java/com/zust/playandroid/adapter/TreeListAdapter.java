package com.zust.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.ui.homepage.detial.ArticleDetialActivity;

import java.util.List;

public class TreeListAdapter extends RecyclerView.Adapter<TreeListAdapter.ViewHolder> {

    private Context context;
    private List<ArticleBean> articleBeanList;

    public TreeListAdapter(Context context, List<ArticleBean> articleBeanList) {
        this.context = context;
        this.articleBeanList = articleBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_article_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTitleTextView.setText(Html.fromHtml(articleBeanList.get(position).getTitle()));
        holder.mAuthorTextView.setText("作者：" + articleBeanList.get(position).getAuthor());
        holder.mChapterTextView.setText("分类：" + articleBeanList.get(position).getChapterName());
        holder.mTimeTextView.setText("发布时间：" + articleBeanList.get(position).getNiceDate());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleDetialActivity.class);
                intent.putExtra("id", articleBeanList.get(position).getId());
                intent.putExtra("title", articleBeanList.get(position).getTitle());
                intent.putExtra("link", articleBeanList.get(position).getLink());
                intent.putExtra("isCollect", articleBeanList.get(position).isCollect());
                intent.putExtra("author", articleBeanList.get(position).getAuthor());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;
        TextView mAuthorTextView;
        TextView mChapterTextView;
        TextView mTimeTextView;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.author);
            mChapterTextView = (TextView) itemView.findViewById(R.id.chapter);
            mTimeTextView = (TextView) itemView.findViewById(R.id.publish_time);
        }
    }
}
