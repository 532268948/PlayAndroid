package com.zust.playandroid.utils;

import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.BannerBean;
import com.zust.playandroid.bean.HotWordsBean;
import com.zust.playandroid.bean.KnowledgeTreeBean;
import com.zust.playandroid.bean.LoginBean;
import com.zust.playandroid.bean.NaviBean;
import com.zust.playandroid.bean.ProjectClassifyBean;
import com.zust.playandroid.bean.ProjectListBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.bean.WebSitesBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/4/22
 * 时 间： 13:34
 * 项 目： PlayAndroid
 * 描 述：
 */


public interface PlayAndroidApi {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<ResponseWrapper<LoginBean>> getLoginData(@Field("username")String username,@Field("password")String password);

    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<ResponseWrapper<LoginBean>> getRegisterData(@Field("username")String username,@Field("password")String password,@Field("repassword")String repassword);

    /**
     * 轮滑图片
     * @return
     */
    @GET("banner/json")
    Observable<ResponseWrapper<List<BannerBean>>> getBannerData();

    @GET("article/list/{num}/json")
    Observable<ResponseWrapper<ArticleListBean>> getHomeArticleData(@Path("num") int num);

    @POST("lg/collect/{id}/json")
    Observable<ResponseWrapper<ArticleListBean>> addCollect(@Path("id")int id);

    @GET("tree/json")
    Observable<ResponseWrapper<List<KnowledgeTreeBean>>> getKnowledgeTreeData();

    @GET("article/list/{page}/json")
    Observable<ResponseWrapper<ArticleListBean>> getKnowledgeHierarchyDetailData(@Path("page") int page, @Query("cid") int id);

    @GET("navi/json")
    Observable<ResponseWrapper<List<NaviBean>>> getNaviData();

    @GET("project/tree/json")
    Observable<ResponseWrapper<List<ProjectClassifyBean>>> getProjectClassifyData();

    @GET("project/list/{page}/json")
    Observable<ResponseWrapper<ProjectListBean>> getProjectListData(@Path("page") int page,@Query("cid") int id);

    @GET("hotkey/json")
    Observable<ResponseWrapper<List<HotWordsBean>>> getHotWordsData();

    @GET("friend/json")
    Observable<ResponseWrapper<List<WebSitesBean>>> getWebSitesData();

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<ResponseWrapper<ArticleListBean>> getSearchData(@Path("page")int page, @Field("k")String k);

    @GET("lg/collect/list/{page}/json")
    Observable<ResponseWrapper<ArticleListBean>> getCollectData(@Path("page")int page);
}
