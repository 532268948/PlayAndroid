package com.zust.playandroid.ui.homepage.detial;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class BannerDetailActivity extends BaseActivity<BannerDetailContract.View,BannerDetailPresenter<BannerDetailContract.View>> implements BannerDetailContract.View {

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
    protected BannerDetailPresenter<BannerDetailContract.View> createPresenter() {
        return new BannerDetailPresenter<>();
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
            String url = request.getUrl().toString();
//            Log.e("should()",url);
            view.loadUrl(url);
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
            }
            return true;
//            return super.shouldOverrideUrlLoading(view, request);
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
