package com.zust.playandroid.contract.project;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ProjectClassifyBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:51
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface ProjectContract {
    interface View extends BaseView{
        void showProjectClassifyData(List<ProjectClassifyBean>projectClassifyBeanList);
    }
    interface Presenter{
        void getProjectClassifyData();
    }
}
