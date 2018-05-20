package com.zust.playandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/17
 * 时 间： 20:53
 * 项 目： PlayAndroid
 * 描 述：
 */


public class NaviBean implements Serializable {
    private List<ArticleBean> articles;
    private int cid;
    private String name;

    public NaviBean(List<ArticleBean> articles, int cid, String name) {
        this.articles = articles;
        this.cid = cid;
        this.name = name;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
