package com.zust.playandroid.contract.navi;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.NaviBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:45
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface NaviContract {
    interface View extends BaseView{
        void showData(List<NaviBean> naviBeanList);
    }
    interface Presenter{
        void getNaviData();
    }
}
