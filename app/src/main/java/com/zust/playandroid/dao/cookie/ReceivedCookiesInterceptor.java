package com.zust.playandroid.dao.cookie;

import android.content.Context;
import android.content.SharedPreferences;

import com.zust.playandroid.dao.db.PlayAndroidPreference;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/9
 * 时 间： 11:27
 * 项 目： PlayAndroid
 * 描 述：
 */


public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            PlayAndroidPreference.getInstance(context).setCookie(cookies);
        }
//        if (!(originalResponse.headers("Set-Cookie")).isEmpty()) {
////            Observable.create(new ObservableOnSubscribe<HashSet<String>>() {
////                @Override
////                public void subscribe(ObservableEmitter<HashSet<String>> emitter) throws Exception {
//                    try {
//                        StringBuilder sb = new StringBuilder();
//                        for (String header:originalResponse.headers("Set-Cookie")){
//                            sb.append(header).append(",");
//                        }
//                        PlayAndroidPreference.getInstance(context).setCookie(sb.toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).subscribe();

//            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("cookie", cookieBuffer.toString());
//            editor.commit();
//        }

        return originalResponse;
    }
}
