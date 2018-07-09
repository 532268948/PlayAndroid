package com.zust.playandroid.presenter.search;

import android.util.Log;

import com.zust.playandroid.base.Observer.BaseObserver;
import com.zust.playandroid.base.presenter.BasePresenter;
import com.zust.playandroid.bean.ArticleBean;
import com.zust.playandroid.bean.ArticleListBean;
import com.zust.playandroid.bean.HotWordsBean;
import com.zust.playandroid.bean.ResponseWrapper;
import com.zust.playandroid.bean.WebSitesBean;
import com.zust.playandroid.bean.db.History;
import com.zust.playandroid.contract.search.SearchContract;
import com.zust.playandroid.dao.db.HistoryUtils;
import com.zust.playandroid.utils.PlayAndroidService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/20
 * 时 间： 13:07
 * 项 目： PlayAndroid
 * 描 述：
 */


public class SearchPresenter<V extends SearchContract.View> extends BasePresenter<V> implements SearchContract.Presenter {

    private HistoryUtils historyUtils;

    @Override
    public void getHistoryData() {
        historyUtils=new HistoryUtils(context.get());
        addDiaposable(Observable.create(new ObservableOnSubscribe<List<History>>() {
            @Override
            public void subscribe(ObservableEmitter<List<History>> emitter) throws Exception {
                emitter.onNext(historyUtils.queryAllHistory());
            }
        })
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<List<History>>(view.get()) {
            @Override
            public void onNext(List<History> historyList) {
                view.get().showHistoryData(historyList);
            }
        }));
    }

    @Override
    public void getHotWordsData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getHotWrodsData()
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<ResponseWrapper<List<HotWordsBean>>>(view.get()) {
            @Override
            public void onNext(ResponseWrapper<List<HotWordsBean>> responseWrapper ) {
                if (responseWrapper.getErrorCode()==0){
                    view.get().showHotData(responseWrapper.getData());
                }else {
                    view.get().showMessage(responseWrapper.getErrorMsg());
                }
            }
        }));
    }

    @Override
    public void getWebSitesData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getWebSitesData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<List<WebSitesBean>>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<List<WebSitesBean>> responseWrapper ) {
                        if (responseWrapper.getErrorCode()==0){
                            view.get().showWebSitesData(responseWrapper.getData());
                        }else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
                    }
                }));
    }

    @Override
    public void saveHistory() {
        addDiaposable(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                historyUtils.insertHistory(new History(null,view.get().getSearchContent(),df.format(new Date()).toString()));
                emitter.onNext(1);
            }
        })
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<Integer>(view.get()) {
            @Override
            public void onNext(Integer integer) {
                refreshHistoryData();
            }
        }));
    }

    @Override
    public void getSearchData() {
        addDiaposable((Disposable) PlayAndroidService.getInstance(context.get()).getSearchData(0,view.get().getSearchContent())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<ResponseWrapper<ArticleListBean>>(view.get()) {
                    @Override
                    public void onNext(ResponseWrapper<ArticleListBean> responseWrapper ) {
                        if (responseWrapper.getErrorCode()==0){
                            Log.e("searchpresenter","getSearchData()");
                            saveHistory();
                            view.get().showSearchData(responseWrapper.getData().getDatas());
                        }else {
                            view.get().showMessage(responseWrapper.getErrorMsg());
                        }
//                        refreshHistoryData();
                    }
                }));
    }

    @Override
    public void refreshHistoryData() {
        if (historyUtils==null){
            historyUtils=new HistoryUtils(context.get());
        }
        addDiaposable(Observable.create(new ObservableOnSubscribe<List<History>>() {
            @Override
            public void subscribe(ObservableEmitter<List<History>> emitter) throws Exception {
                emitter.onNext(historyUtils.queryAllHistory());
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<List<History>>(view.get()) {
                    @Override
                    public void onNext(List<History> historyList) {
                        view.get().refreshHistoryData(historyList);
                    }
                }));
    }

    @Override
    public void deleteHistoryData() {
        if(historyUtils==null){
            historyUtils=new HistoryUtils(context.get());
        }
        addDiaposable((Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                historyUtils.deleteAll();
                emitter.onNext(1);
            }
        }).subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<Integer>(view.get()) {
            @Override
            public void onNext(Integer integer) {
                if (integer==1) {
                    view.get().showMessage("历史搜索信息已清除");
                    view.get().refreshHistoryData(null);
                }else {
                    view.get().showMessage("出现未知错误");
                }
            }
        })));
    }
}
