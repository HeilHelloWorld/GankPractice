package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.BasicGankBean;
import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.enity.GankBean;
import com.example.lichu.gankpractice.presenter.DataPresenter;
import com.example.lichu.gankpractice.utils.RxUtils;

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

    public Observable<List<BasicGankBean>> getGankBeanByNetwork(String type, int size, int page) {
        return this.mGankDataModel.getData(type, size, page)
                .map(gankBean -> gankBean.results)
                .compose(RxUtils.applyIoToMainSchedulers());
    }
}
