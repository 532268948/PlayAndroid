package com.zust.playandroid.ui.search;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.search.SearchContract;
import com.zust.playandroid.presenter.search.SearchPresenter;

public class SearchActivity extends BaseActivity<SearchContract.View, SearchPresenter<SearchContract.View>> implements SearchContract.View {

    private ImageView mBackImageView;
    private EditText mSearchEditText;
    private Button mSearchButton;
    private TextView mHistoryView;
    private TextView mHotView;
    private TextView mWebView;
    private FlowLayout mHistorFlow;
    private FlowLayout mHotFlow;
    private FlowLayout mWebFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().setStatusBarColor(getResources().getColor(R.color.button_progress_blue));

        mBackImageView=(ImageView)findViewById(R.id.back);
        mSearchEditText=(EditText)findViewById(R.id.search_content);
        mSearchButton=(Button)findViewById(R.id.search);
        mHistoryView=(TextView)findViewById(R.id.history);
        mHotView=(TextView)findViewById(R.id.hot);
        mWebView=(TextView)findViewById(R.id.web);
        mHistorFlow=(FlowLayout)findViewById(R.id.flow_search_history);
        mHotFlow=(FlowLayout)findViewById(R.id.flow_hot_words);
        mWebFlow=(FlowLayout)findViewById(R.id.flow_web);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
