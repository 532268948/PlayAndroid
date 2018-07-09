package com.zust.playandroid.contract.tree;

import com.zust.playandroid.base.view.BaseView;
import com.zust.playandroid.bean.ArticleBean;

import java.util.List;

public interface Tree2Contract {
    interface View extends BaseView{
        void showData();
    }
    interface Presenter{
        void getData();
    }
}
