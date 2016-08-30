package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.gank.GankPractice;

import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 */
public class GankDailyModel implements I_DailyModel {

    private static final GankDailyModel INSTANCE = new GankDailyModel();

    private GankDailyModel(){}

    public static GankDailyModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<DailyGankBean> getDaily(int year, int month, int day) {
        return  GankPractice.getInstance().getGankService().getDaily(year, month, day);
    }
}
