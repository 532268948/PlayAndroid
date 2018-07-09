package com.zust.playandroid.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zust.playandroid.R;
import com.zust.playandroid.adapter.SearchAdapter;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.HotWordsBean;
import com.zust.playandroid.bean.WebSitesBean;
import com.zust.playandroid.bean.db.History;
import com.zust.playandroid.contract.search.SearchContract;
import com.zust.playandroid.presenter.search.SearchPresenter;
import com.zust.playandroid.ui.navi.detail.NaviDetailActivity;
import com.zust.playandroid.ui.search.detail.SearchDetailActivity;
import com.zust.playandroid.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchContract.View, SearchPresenter<SearchContract.View>> implements SearchContract.View {

    private static final String TAG = SearchActivity.class.getName();
    private int STATUS = 0;

    private ImageView mBackImageView;
    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mHistoryView;
    private TextView mHotView;
    private TextView mWebView;
    private TagFlowLayout mHistoryFlow;
    private TagFlowLayout mHotFlow;
    private TagFlowLayout mWebFlow;
    private SmartRefreshLayout mRefreshLayout;
    private ScrollView mWordsLayout;
    private RecyclerView mRecyclerView;
    private ImageView mDeleteImageView;

    private List<History> historyList;
    private List<HotWordsBean> hotWordsBeanList;
    private List<WebSitesBean> webSitesBeanList;
    private List<ArticleBean> mSearchList = new ArrayList<>();

    private SearchAdapter mSearchAdapter;
    private HistoryAdapter mHistoryAdapter;
    private HotAdapter mHotAdapter;
    private WebAdapter mWebAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().setStatusBarColor(getResources().getColor(R.color.button_progress_blue));

        mBackImageView = (ImageView) findViewById(R.id.back);
        mSearchEditText = (EditText) findViewById(R.id.search_content);
        mSearchButton = (Button) findViewById(R.id.search);
        mHistoryView = (TextView) findViewById(R.id.history);
        mHotView = (TextView) findViewById(R.id.hot);
        mWebView = (TextView) findViewById(R.id.web);
        mHistoryFlow = (TagFlowLayout) findViewById(R.id.flow_search_history);
        mHotFlow = (TagFlowLayout) findViewById(R.id.flow_hot_words);
        mWebFlow = (TagFlowLayout) findViewById(R.id.flow_web);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.bottom);
        mWordsLayout = (ScrollView) findViewById(R.id.key);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDeleteImageView=(ImageView)findViewById(R.id.history_delete);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (STATUS == 0) {
                    finish();
                } else if (STATUS == 1) {
                    mRefreshLayout.setVisibility(View.GONE);
                    mWordsLayout.setVisibility(View.VISIBLE);
                    STATUS = 0;
                }
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchEditText.getText().toString().trim().isEmpty()) {
                    showMessage("搜索内容不能为空");
                } else {
                    Log.e(TAG, "onclick_getSearchData()");
                    presenter.getSearchData();
                    if (mWordsLayout.getVisibility() != View.GONE) {
                        mWordsLayout.setVisibility(View.GONE);
                    }
                    if (mRefreshLayout.getVisibility() != View.VISIBLE) {
                        mRefreshLayout.setVisibility(View.VISIBLE);
                    }
                    STATUS = 1;
                }
            }
        });

        mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteHistoryData();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mSearchAdapter = new SearchAdapter(this, mSearchList);
        mRecyclerView.setAdapter(mSearchAdapter);

        getData();
    }

    @Override
    protected SearchPresenter<SearchContract.View> createPresenter() {
        return new SearchPresenter<>();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.search_fade_out);
    }

    @Override
    public void getData() {
        presenter.getHistoryData();
        presenter.getHotWordsData();
        presenter.getWebSitesData();
    }

    @Override
    public void showHistoryData(List<History> historyList) {
        this.historyList = historyList;
        mHistoryAdapter = new HistoryAdapter(this.historyList, mHistoryFlow);
        mHistoryFlow.setAdapter(mHistoryAdapter);
        mHistoryFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TagClick(1,position);
                return true;
            }
        });
    }

    @Override
    public void showHotData(List<HotWordsBean> hotWordsBeanList) {
        this.hotWordsBeanList = hotWordsBeanList;
        mHotAdapter = new HotAdapter(this.hotWordsBeanList, mHotFlow);
        mHotFlow.setAdapter(mHotAdapter);
        mHotFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TagClick(2,position);
                return true;
            }
        });
    }

    @Override
    public void showWebSitesData(final List<WebSitesBean> webSitesBeanList) {
        this.webSitesBeanList = webSitesBeanList;
        mWebAdapter = new WebAdapter(this.webSitesBeanList, mWebFlow);
        mWebFlow.setAdapter(mWebAdapter);
        mWebFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TagClick(3,position);
                return true;
            }
        });
    }

    @Override
    public void refreshHistoryData(List<History> historyList) {
        this.historyList.clear();
        if (historyList!=null) {
            this.historyList.addAll(historyList);
        }
        mHistoryAdapter.notifyDataChanged();
    }

    @Override
    public String getSearchContent() {
        return mSearchEditText.getText().toString();
    }

    @Override
    public void showSearchData(List<ArticleBean> articleBeanList) {
        Log.e(TAG, articleBeanList.size() + "");
        this.mSearchList.clear();
        this.mSearchList.addAll(articleBeanList);
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (STATUS==0){
            finish();
        }else if (STATUS==1){
            presenter.refreshHistoryData();
            mRefreshLayout.setVisibility(View.GONE);
            mWordsLayout.setVisibility(View.VISIBLE);
            STATUS=0;
        }
    }

    @Override
    public void TagClick(int type,int position) {
        switch (type){
            case 1:
                mSearchEditText.setText(this.historyList.get(position).getContent());
                mSearchButton.performClick();
                break;
            case 2:
                mSearchEditText.setText(this.hotWordsBeanList.get(position).getName());
                mSearchButton.performClick();
                break;
            case 3:
                Intent intent=new Intent(this, SearchDetailActivity.class);
                intent.putExtra("id",webSitesBeanList.get(position).getId());
                intent.putExtra("title",webSitesBeanList.get(position).getName());
                intent.putExtra("link",webSitesBeanList.get(position).getLink());
                startActivity(intent);
                break;
            default:
                break;
        }

    }

    static class HistoryAdapter extends TagAdapter<History> {

        private TagFlowLayout mTagFlowLayout;

        public HistoryAdapter(List<History> datas, TagFlowLayout tagFlowLayout) {
            super(datas);
            this.mTagFlowLayout = tagFlowLayout;
        }

        @Override
        public View getView(FlowLayout parent, int position, History history) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_flow_item, mTagFlowLayout, false);
            textView.setTextColor(CommonUtil.randomColor());
            textView.setText(history.getContent());
            return textView;
        }
    }

    static class HotAdapter extends TagAdapter<HotWordsBean> {

        private TagFlowLayout mTagFlowLayout;

        public HotAdapter(List<HotWordsBean> datas, TagFlowLayout tagFlowLayout) {
            super(datas);
            this.mTagFlowLayout = tagFlowLayout;
        }

        @Override
        public View getView(FlowLayout parent, int position, HotWordsBean hotWordsBean) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_flow_item, mTagFlowLayout, false);
            textView.setTextColor(CommonUtil.randomColor());
            textView.setText(hotWordsBean.getName());
            return textView;
        }
    }

    static class WebAdapter extends TagAdapter<WebSitesBean> {

        private TagFlowLayout mTagFlowLayout;

        public WebAdapter(List<WebSitesBean> datas, TagFlowLayout tagFlowLayout) {
            super(datas);
            this.mTagFlowLayout = tagFlowLayout;
        }

        @Override
        public View getView(FlowLayout parent, int position, WebSitesBean webSitesBean) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_flow_item, mTagFlowLayout, false);
            textView.setTextColor(CommonUtil.randomColor());
            textView.setText(webSitesBean.getName());
            return textView;
        }
    }
}
