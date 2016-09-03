package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.BasicGankBean;
import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.enity.GankBean;
import com.example.lichu.gankpractice.presenter.DataPresenter;
import com.example.lichu.gankpractice.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 * for easily managing data in presenter
 */
public class DataManager {
    private static DataManager manager;

    private GankDailyModel mGankDailyModel;
    private GankDataModel mGankDataModel;

    private DataManager() {
        this.mGankDailyModel = GankDailyModel.getInstance();
        this.mGankDataModel = GankDataModel.getInstance();
    }

    public synchronized static DataManager getInstance() {
        if (manager == null) {
            manager = new DataManager();
        }
        return manager;
    }

    public Observable<List<DailyGankBean>> getDailyDataByNetwork(DataPresenter.EasyDate currentDate) {
        return Observable.just(currentDate)
                .flatMapIterable(DataPresenter.EasyDate::getPastTime)
                .flatMap(easyDate -> this.mGankDailyModel.getDaily(easyDate.getYear(), easyDate.getMonth(), easyDate.getDay()).filter(dailyGankBean -> dailyGankBean.results.androidData != null))
                .toSortedList((dailyGankBean1, dailyGankBean2) -> dailyGankBean1.results.androidData.get(0).publishedAt.compareTo(dailyGankBean2.results.androidData.get(0).publishedAt))
                .compose(RxUtils.applyIoToMainSchedulers());
    }

    public Observable<List<BasicGankBean>> getDataByNetwork(String type, int size, int page) {
        return this.mGankDataModel.getData(type, size, page)
                .map(gankBean -> gankBean.results)
                .compose(RxUtils.applyIoToMainSchedulers());
    }
    
    public Observable<ArrayList<ArrayList<BasicGankBean>>> getDailyDetailByDailyResultss(DailyGankBean.DailyResults results) {
        return Observable.just(results)
                         .compose(RxUtils.applyIoToMainSchedulers())
                         .map(dailyResults -> {
                             ArrayList<ArrayList<BasicGankBean>> dailyDetail = new ArrayList<ArrayList<BasicGankBean>>();
                             if (dailyResults.welfareData != null && dailyResults.welfareData.size() > 0) {
                                 dailyDetail.add(dailyResults.welfareData);
                             }
                             if (dailyResults.androidData != null && dailyResults.androidData.size() > 0) {
                                 dailyDetail.add(dailyResults.androidData);
                             }
                             if (dailyResults.iosData != null && dailyResults.iosData.size() > 0) {
                                 dailyDetail.add(dailyResults.iosData);
                             }
                             if (dailyResults.jsData != null && dailyResults.jsData.size() > 0) {
                                 dailyDetail.add(dailyResults.jsData);
                             }
                             if (dailyResults.videoData != null && dailyResults.videoData.size() > 0) {
                                 dailyDetail.add(dailyResults.videoData);
                             }
                             if (dailyResults.resourcesData != null && dailyResults.resourcesData.size() > 0) {
                                 dailyDetail.add(dailyResults.resourcesData);
                             }
                             if (dailyResults.appData != null && dailyResults.appData.size() > 0) {
                                 dailyDetail.add(dailyResults.appData);
                             }
                             if (dailyResults.recommendData != null && dailyResults.recommendData.size() > 0) {
                                 dailyDetail.add(dailyResults.recommendData);
                             }
                             return dailyDetail;
                         }); 
    }
}
