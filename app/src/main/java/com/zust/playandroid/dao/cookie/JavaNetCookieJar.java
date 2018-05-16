package com.zust.playandroid.dao.cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/14
 * 时 间： 9:42
 * 项 目： PlayAndroid
 * 描 述：
 */


public final class JavaNetCookieJar implements CookieJar {

    private CookieHandler cookieHandler;

    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookieHandler != null) {
            List<String> cookieStrings = new ArrayList<>();
            for (Cookie cookie : cookies) {//遍历cookie集合
                cookieStrings.add(cookie.toString());
            }
            Map<String, List<String>> multimap = Collections.singletonMap("Set-Cookie", cookieStrings);
            try {
                //具体的保存工作交给cookieHandler去处理
                cookieHandler.put(url.uri(), multimap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        Map<String, List<String>> headers = Collections.emptyMap();
        Map<String, List<String>> cookieHeaders;
        try {
            //从cookieHandler中取出cookie集合。
            cookieHeaders = cookieHandler.get(url.uri(), headers);
        } catch (IOException e) {
            return Collections.emptyList();
        }

        List<Cookie> cookies = null;
        for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
            String key = entry.getKey();
            if (("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key))
                    && !entry.getValue().isEmpty()) {
                for (String header : entry.getValue()) {
                    if (cookies == null) cookies = new ArrayList<>();
                    cookies.addAll(decodeHeaderAsJavaNetCookies(url, header));
                }
            }
        }

        return cookies != null
                ? Collections.unmodifiableList(cookies)
                : Collections.<Cookie>emptyList();
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList<>();
        for (int pos = 0, limit = header.length(), pairEnd = 0; pos < limit; pos = pairEnd + 1) {
            
        }
        return result;
    }
}
