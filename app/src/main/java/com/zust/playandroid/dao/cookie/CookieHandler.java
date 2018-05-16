package com.zust.playandroid.dao.cookie;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/14
 * 时 间： 12:03
 * 项 目： PlayAndroid
 * 描 述：
 */


public abstract class CookieHandler {
    private static CookieHandler cookieHandler;

    public synchronized static CookieHandler getDefault() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
//            sm.checkPermission(SecurityConstants.GET_COOKIEHANDLER_PERMISSION);
        }
        return cookieHandler;
    }

    public synchronized static void setDefault(CookieHandler cHandler) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
//            sm.checkPermission(SecurityConstants.SET_COOKIEHANDLER_PERMISSION);
        }
        cookieHandler = cHandler;
    }

    public abstract Map<String, List<String>>
    get(URI uri, Map<String, List<String>> requestHeaders)
            throws IOException;

    public abstract void
    put(URI uri, Map<String, List<String>> responseHeaders)
            throws IOException;
}
