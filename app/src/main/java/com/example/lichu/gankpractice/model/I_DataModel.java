package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.GankBean;

import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 */
public interface I_DataModel {
    Observable<GankBean> getData(String type, int size, int page);
}
