package com.zust.playandroid.contract.main;

import com.zust.playandroid.base.view.BaseView;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/3
 * 时 间： 13:11
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface MainContract {

    interface View extends BaseView{
        void titleChangeAnimation(float rotation);
        void setTitle();
        void searchShow();
        boolean isAnimating();
        void cancelAnimation();
        void Logout();
    }

    interface Presenter{
        void titleChangeAnimation();
        void Logout();
    }

}
