package com.zust.playandroid.contract.search;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.HotWordsBean;
import com.zust.playandroid.bean.WebSitesBean;
import com.zust.playandroid.bean.db.History;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/20
 * 时 间： 13:08
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface SearchContract {
    interface View extends BaseView{
        void getData();
        void showHistoryData(List<History> historyList);
        void showHotData(List<HotWordsBean> hotWordsBeanList);
        void showWebSitesData(List<WebSitesBean> webSitesBeanList);
        void refreshHistoryData(List<History> historyList);
        String getSearchContent();
        void showSearchData(List<ArticleBean> articleBeanList);
        void TagClick(int type,int position);

    }
    interface Presenter{
        void getHistoryData();
        void getHotWordsData();
        void getWebSitesData();
        void saveHistory();
        void getSearchData();
        void refreshHistoryData();
    }
}
