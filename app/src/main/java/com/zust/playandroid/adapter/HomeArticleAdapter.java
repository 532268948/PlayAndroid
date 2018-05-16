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
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zust.playandroid.R;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.BannerBean;
import com.zust.playandroid.ui.homepage.detial.ArticleDetialActivity;
import com.zust.playandroid.ui.homepage.detial.BannerDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/7
 * 时 间： 10:32
 * 项 目： PlayAndroid
 * 描 述：
 */


public class HomeArticleAdapter extends RecyclerView.Adapter<HomeArticleAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private View mHeaderView;
    private View mFooterView;
    private List<ArticleBean> articleBeanList;
    private List<BannerBean> bannerList;
    private List<String> imageList=new ArrayList<>();
    private Context context;

    public HomeArticleAdapter(List<ArticleBean> articleBeanList, List<BannerBean> bannerList, Context context) {
        this.articleBeanList = articleBeanList;
        this.bannerList = bannerList;
        this.context = context;
        imageList.clear();
        for (BannerBean bannerBean:bannerList){
            imageList.add(bannerBean.getImagePath());
        }
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1&&mFooterView!=null) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_article_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof HomeArticleAdapter.ViewHolder) {
                holder.mTitleTextView.setText(articleBeanList.get(position-1).getTitle());
                holder.mAuthorTextView.setText("作者："+articleBeanList.get(position-1).getAuthor());
                holder.mChapterTextView.setText("分类："+articleBeanList.get(position-1).getChapterName());
                holder.mTimeTextView.setText("发布时间："+articleBeanList.get(position-1).getNiceDate());
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, ArticleDetialActivity.class);
                        intent.putExtra("id",articleBeanList.get(position-1).getId());
                        intent.putExtra("title",articleBeanList.get(position-1).getTitle());
                        intent.putExtra("link",articleBeanList.get(position-1).getLink());
                        intent.putExtra("isCollect",articleBeanList.get(position-1).isCollect());
                        intent.putExtra("author",articleBeanList.get(position-1).getAuthor());
                        context.startActivity(intent);
                    }
                });
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {

            holder.banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            holder.banner.setImages(imageList);
            holder.banner.start();
            holder.banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent=new Intent(context, BannerDetailActivity.class);
                    intent.putExtra("title",bannerList.get(position).getTitle());
                    intent.putExtra("link",bannerList.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
            return;
        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return articleBeanList.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return articleBeanList.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return articleBeanList.size() + 1;
        } else {
            return articleBeanList.size() + 2;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Banner banner;
        TextView mTitleTextView;
        TextView mAuthorTextView;
        TextView mChapterTextView;
        TextView mTimeTextView;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                banner = (Banner) itemView.findViewById(R.id.banner);
                return;
            }
            if (itemView == mFooterView) {

                return;
            }
            mCardView=(CardView)itemView.findViewById(R.id.cardview);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title);
            mAuthorTextView=(TextView)itemView.findViewById(R.id.author);
            mChapterTextView=(TextView)itemView.findViewById(R.id.chapter);
            mTimeTextView=(TextView)itemView.findViewById(R.id.publish_time);
        }
    }
}
