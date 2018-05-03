package com.zust.playandroid.utils;

import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.bean.ResponseWrapper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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


public class PlayAndroidService{

    private volatile static PlayAndroidService instance = null;
    private static final String baseUrl = "http://www.wanandroid.com/";
    private PlayAndroidApi androidApi;
    private Retrofit retrofit;

    private PlayAndroidService() {
        //设置超时时间
        OkHttpClient.Builder httpcientBuilder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpcientBuilder.build())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        androidApi = retrofit.create(PlayAndroidApi.class);
    }

    public static PlayAndroidService getInstance() {
        if (instance == null) {
            synchronized (PlayAndroidService.class) {
                if (instance == null) {
                    instance = new PlayAndroidService();
                }
            }
        }
        return instance;
    }

    public Observable getLoginData( String username, String password) {
        return androidApi.getLoginData(username, password);
    }

    public Observable getRegisterData(String username,String password,String repassword){
        return androidApi.getRegisterData(username, password, repassword);
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
    }

}
