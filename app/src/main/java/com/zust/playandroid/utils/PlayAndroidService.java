package com.zust.playandroid.utils;

import android.content.Context;

import com.zust.playandroid.dao.cookie.AddCookiesInterceptor;
import com.zust.playandroid.dao.cookie.ReceivedCookiesInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 13:55
 * 项 目： PlayAndroid
 * 描 述：
 */


public class PlayAndroidService {

    private volatile static PlayAndroidService instance = null;
    private static final String baseUrl = "http://www.wanandroid.com/";
    private PlayAndroidApi androidApi;
    private Retrofit retrofit;
    private Context context;

    private PlayAndroidService(Context context) {
        //设置超时时间
        this.context = context;

        OkHttpClient.Builder httpcientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .connectTimeout(5, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpcientBuilder.build())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        androidApi = retrofit.create(PlayAndroidApi.class);
    }

    public static PlayAndroidService getInstance(Context context) {
        if (instance == null) {
            synchronized (PlayAndroidService.class) {
                if (instance == null) {
                    instance = new PlayAndroidService(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public Observable getLoginData(String username, String password) {
        return androidApi.getLoginData(username, password);
    }

    public Observable getRegisterData(String username, String password, String repassword) {
        return androidApi.getRegisterData(username, password, repassword);
    }

    public Observable getBannerData() {
        return androidApi.getBannerData();
    }

    public Observable getHomeArticleData(int num) {
        return androidApi.getHomeArticleData(num);
    }

    public Observable addCollect(int id) {
        return androidApi.addCollect(id);
    }

    public Observable getKnowledgeTreeData() {
        return androidApi.getKnowledgeTreeData();
    }

    public Observable getKnowledgeHierarchyDetailData(int page, int id) {
        return androidApi.getKnowledgeHierarchyDetailData(page, id);
    }

    public Observable getNaviData() {
        return androidApi.getNaviData();
    }

    public Observable getProjectClassifyData() {
        return androidApi.getProjectClassifyData();
    }

    public Observable getProjectListData(int page, int id) {
        return androidApi.getProjectListData(page, id);
    }

    public Observable getHotWrodsData() {
        return androidApi.getHotWordsData();
    }

    public Observable getWebSitesData() {
        return androidApi.getWebSitesData();
    }

    public Observable getSearchData(int page, String k) {
        return androidApi.getSearchData(page, k);
    }

    public Observable getCollectData(int page) {
        return androidApi.getCollectData(page);
    }
}
