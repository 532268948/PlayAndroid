package com.zust.playandroid.ui.homepage.detial;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.base.activity.BaseActivity;
import com.zust.playandroid.contract.homepage.detial.ArticleDetialContract;
import com.zust.playandroid.dao.db.PlayAndroidPreference;
import com.zust.playandroid.presenter.homepage.detial.ArticleDetialPresenter;
import com.zust.playandroid.utils.ToastUtil;

import java.lang.reflect.Method;

public class ArticleDetialActivity extends BaseActivity<ArticleDetialContract.View, ArticleDetialPresenter<ArticleDetialContract.View>> implements
        ArticleDetialContract.View,
        Toolbar.OnMenuItemClickListener{

    private WebView mWebView;
    private TextView mTitleTextView;

    private int id;
    private String title;
    private String link;
    private String author;
    private boolean isCollect;

    private MenuItem mCollectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detial);

        Log.e("Detail", PlayAndroidPreference.getInstance(this).getCookie());

        getWindow().setStatusBarColor(getResources().getColor(R.color.button_progress_blue));



        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        ToastUtil.shortToast(id+"");
        title=intent.getStringExtra("title");
        link=intent.getStringExtra("link");
        author=intent.getStringExtra("author");
        isCollect=intent.getBooleanExtra("isCollect",false);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(this);

        mWebView = (WebView) findViewById(R.id.web_view);
//        mTitleTextView=(TextView)findViewById(R.id.title);

//        mTitleTextView.setText(title);

        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.requestFocus();
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(link);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_detail_toorbar_menu,menu);
        mCollectItem = menu.findItem(R.id.collect);
        if (isCollect) {
            mCollectItem.setTitle(getString(R.string.toolbar_menu_cancel_collect));
            mCollectItem.setIcon(R.drawable.account);
        } else {
            mCollectItem.setTitle(getString(R.string.toolbar_menu_collect));
            mCollectItem.setIcon(R.drawable.password);
        }
        return true;
    }

    @Override
    protected ArticleDetialPresenter<ArticleDetialContract.View> createPresenter() {
        return new ArticleDetialPresenter<>();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.e("Activity","onMenuItemClick()");
        switch (item.getItemId()){
            case R.id.collect:
                collectEvent();
                break;
            default:
                break;
        }
        return true;
    }

    private void collectEvent() {
        Log.e("Activity","collectEvent()");
        if (isCollect){
            presenter.cancelCollect();
        }else {
            presenter.addCollect();
        }
    }

    @Override
    public int getId() {
        return id;
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
