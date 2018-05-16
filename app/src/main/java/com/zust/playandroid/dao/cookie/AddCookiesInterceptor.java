package com.zust.playandroid.dao.cookie;

import android.content.Context;

import com.zust.playandroid.dao.db.PlayAndroidPreference;

import java.io.IOException;
import java.util.HashSet;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/9
 * 时 间： 12:11
 * 项 目： PlayAndroid
 * 描 述：
 */


public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
//
//                for(String cookie:PlayAndroidPreference.getInstance(context).getCookie().split(",")){
//                    builder.addHeader("Cookie",cookie.substring(0,cookie.length()-1));
//                }
//        builder.addHeader("Cookie", PlayAndroidPreference.getInstance(context).getCookie());
        HashSet<String> preferences = (HashSet) PlayAndroidPreference.getInstance(context).getCookie(new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
//            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}
