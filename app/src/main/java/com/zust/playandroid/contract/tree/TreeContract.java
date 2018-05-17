package com.zust.playandroid.contract.tree;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.KnowledgeTreeBean;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/4
 * 时 间： 16:40
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface TreeContract {
    interface View extends BaseView{
        void showData(List<KnowledgeTreeBean> knowledgeTreeBeanList);
    }

    interface Presenter{
        void getData();
    }
}
