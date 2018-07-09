package com.zust.playandroid.contract.tree;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ArticleBean;

import java.util.List;

public interface TreeDetailContract {
    interface View extends BaseView{
        void showData(List<ArticleBean> articleBeanList);
        int getTreeId();
        void getRefreshData(List<ArticleBean> articleBeanList);
        void getLoadData(List<ArticleBean> articleBeanList);
        int getPage();
    }
    interface Presenter{
        void getData();
        void Refresh();
        void LoadMore();
    }
}
