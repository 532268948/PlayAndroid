package com.zust.playandroid.contract.register;

import com.zust.playandroid.base.view.BaseView;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/23
 * 时 间： 15:09
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface RegisterContract {
    interface View extends BaseView{
        String getName();
        String getPassword();
        String getRePassword();
        void SuccessAnimation();
        void FalseAnimation();
        void gotoGuideActivity();
        void gotoMainActivity();
    }

    interface Presenter{
        void getRegisterData();
        void savePersonInformation(String username,String password);
    }

}
