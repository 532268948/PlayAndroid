package com.zust.playandroid.contract.homepage;

import android.widget.ImageView;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.BannerBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:37
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface HomeContract {
    interface View extends BaseView{
//        void showBannerImage(List<String> imagePathList);
        void showArticleList(ArticleListBean articleListBean,List<BannerBean> imagePathList);
        int getPage();
        void refreshArticle(ArticleListBean articleListBean);
        void refreshSuccess();
        void refreshFalse();
        void loadMoreArticle(ArticleListBean articleListBean);
        void loadSuccess();
        void loadFalse();
    }

    interface Presenter{
        void getBannerData();
        void getArticleData();
        void refreshArticle();
        void loadMoreArticle();
//        void changeDataToImageView(List<BannerBean> beanList);
    }
}
