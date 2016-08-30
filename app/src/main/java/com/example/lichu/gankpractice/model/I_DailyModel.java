package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.DailyGankBean;

import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 */
public interface I_DailyModel {
    Observable<DailyGankBean> getDaily(int year, int month, int day);
}
