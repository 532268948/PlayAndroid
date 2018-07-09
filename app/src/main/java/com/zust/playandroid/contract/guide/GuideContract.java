package com.zust.playandroid.contract.guide;

import com.zust.playandroid.base.view.BaseView;

public interface GuideContract {
    interface View extends BaseView{
        void gotoMainActivity();
    }

    interface Presennter{
        void init();
    }
}
