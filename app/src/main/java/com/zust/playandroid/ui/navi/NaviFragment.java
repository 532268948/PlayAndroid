package com.zust.playandroid.ui.navi;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zust.playandroid.R;
import com.zust.playandroid.adapter.NaviAdapter;
import com.zust.playandroid.base.fragment.BaseFragment;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.NaviBean;
import com.zust.playandroid.contract.navi.NaviContract;
import com.zust.playandroid.presenter.navi.NaviPresenter;
import com.zust.playandroid.ui.navi.detail.NaviDetailActivity;
import com.zust.playandroid.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class NaviFragment extends BaseFragment<NaviContract.View,NaviPresenter<NaviContract.View>> implements NaviContract.View {

    private List<NaviBean> naviBeanList;
    private List<ArticleBean> articleBeanList=new ArrayList<>();

    private RecyclerView mRecyclerView;
//    private RecyclerView mItemRecyclerView;
    private TagFlowLayout mTagFlowLayout;

    private NaviAdapter mNaviAdapter;
    private TagAdapter mTagAdapter;

    @Override
    protected NaviPresenter<NaviContract.View> createPresenter() {
        return new NaviPresenter<>();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_navi,container,false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.title);
//        mItemRecyclerView=(RecyclerView)view.findViewById(R.id.item_title);
        mTagFlowLayout=(TagFlowLayout)view.findViewById(R.id.item);

        return view;
    }

    @Override
    public void initData() {
        presenter.getNaviData();
    }

    @Override
    public void showMessage(String message) {

    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showData(final List<NaviBean> naviBeanList) {
        this.naviBeanList=naviBeanList;
        this.articleBeanList.addAll(this.naviBeanList.get(0).getArticles());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mNaviAdapter=new NaviAdapter(getContext(),naviBeanList);
        mRecyclerView.setAdapter(mNaviAdapter);
        mTagAdapter=new ItemAdapter(this.articleBeanList,mTagFlowLayout);
        mTagFlowLayout.setAdapter(mTagAdapter);
        setClickListener();

    }

    private void setClickListener() {
        mNaviAdapter.setOnItemClickListener(new NaviAdapter.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                articleBeanList.clear();
                articleBeanList.addAll(naviBeanList.get(position).getArticles());
//                mNaviAdapter.setActived(position,1);
                mTagAdapter.notifyDataChanged();
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent=new Intent(getActivity(), NaviDetailActivity.class);
                intent.putExtra("id",articleBeanList.get(position).getId());
                intent.putExtra("title",articleBeanList.get(position).getTitle());
                intent.putExtra("link",articleBeanList.get(position).getLink());
                intent.putExtra("isCollect",articleBeanList.get(position).isCollect());
                getActivity().startActivity(intent);
                return true;
            }
        });
    }

    static class ItemAdapter extends TagAdapter<ArticleBean>{

        private TagFlowLayout mTagFlowLayout;

        public ItemAdapter(List<ArticleBean> datas,TagFlowLayout tagFlowLayout) {
            super(datas);
            this.mTagFlowLayout=tagFlowLayout;
        }

        @Override
        public View getView(FlowLayout parent, int position, ArticleBean articleBean) {
            TextView textView=(TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_flow_item,mTagFlowLayout,false);
            textView.setTextColor(CommonUtil.randomColor());
            textView.setText(articleBean.getTitle());
            return textView;
        }
    }
}
