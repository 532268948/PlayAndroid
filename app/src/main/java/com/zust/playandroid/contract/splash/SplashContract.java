package com.zust.playandroid.contract.splash;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.LoginBean;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/3
 * 时 间： 15:26
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface SplashContract {
    interface View extends BaseView{
        void gotoMainActivity();
        void gotoLoginActivity();
    }
    interface Presenter{
        void init();
        void getInformation();
        void login(LoginBean loginBean);
    }
}
