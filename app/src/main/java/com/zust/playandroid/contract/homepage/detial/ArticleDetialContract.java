package com.zust.playandroid.contract.homepage.detial;

import com.zust.playandroid.base.view.BaseView;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/7
 * 时 间： 15:20
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface ArticleDetialContract {

    interface View extends BaseView{
        int getId();
        void Back();
        void openByBrowser();
    }

    interface Presenter{
        void addCollect();
        void cancelCollect();
    }
}
