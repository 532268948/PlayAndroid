package com.zust.playandroid.contract.project.list;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ArticleBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/19
 * 时 间： 12:44
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface ProjectListContract {
    interface View extends BaseView{
        int getListId();
        void showProjectListData(List<ArticleBean> articleBeanList);
        void refreshData();
        void loadMoreData();
        boolean refreshSuccess(List<ArticleBean> articleBeanList);
        boolean loadMoreSuccess(List<ArticleBean> articleBeanList);
        int getCurrentPage();
        void setCurrentPage(int page);
        void jumpToTheTop();
    }

    interface Presenter{
        void getProjectListData();
        void getRefreshData();
        void getLoadMoreData();
    }
}
