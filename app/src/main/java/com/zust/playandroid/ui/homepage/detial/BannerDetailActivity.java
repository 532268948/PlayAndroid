package com.zust.playandroid.ui.homepage.detial;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.homepage.detial.BannerDetailContract;
import com.zust.playandroid.presenter.homepage.detial.BannerDetailPresenter;
import com.zust.playandroid.utils.ToastUtil;

import static android.view.KeyEvent.KEYCODE_BACK;

public class BannerDetailActivity extends BaseActivity<BannerDetailContract.View,BannerDetailPresenter<BannerDetailContract.View>> implements BannerDetailContract.View,KeyEvent.Callback {

    private String title;
    private String link;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_detail);

        getWindow().setStatusBarColor(getResources().getColor(R.color.button_progress_blue));

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        link=intent.getStringExtra("link");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.web_view);

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.requestFocus();
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new BannerDetailActivity.MyWebViewClient());
        mWebView.loadUrl(link);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            Back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected BannerDetailPresenter<BannerDetailContract.View> createPresenter() {
        return new BannerDetailPresenter<>();
    }

    @Override
    public void Back() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            this.finish();
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            view.loadUrl(url);
//            Log.e("onPageFinished()",url);
//            imgReset();//重置webview中img标签的图片大小
//             html加载完成之后，添加监听图片的点击js函数
//            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            String url = request.getUrl().toString();
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse(url);
//            intent.setData(content_url);
//            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//            startActivity(intent);
            mWebView.loadUrl(request.getUrl().toString());
            return true;
        }


        private void imgReset() {
            mWebView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }
    }
}
