package com.zust.playandroid.contract.login;

import android.content.Context;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.utils.ResponseListener;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 9:59
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface LoginContract {
    interface View extends BaseView{
        String getUserName();
        String getPassword();
        void gotoMainActivity();
        void gotoGuideActivity();
        void gotoRegisterActivity();
        void loginSuccess();
        void loginFalse();
    }
    interface Presenter{
        void login();
        void gotoGuideActivity();
        void gotoMainActivity();
        void gotoRegisterActivity();
    }
    interface model{
        void getLoginData(String username, String password, ResponseListener<LoginBean> listener);
        boolean isFirst(Context context);
    }
}
