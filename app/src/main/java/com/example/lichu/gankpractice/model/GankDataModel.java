package com.example.lichu.gankpractice.model;

import com.example.lichu.gankpractice.enity.GankBean;
import com.example.lichu.gankpractice.gank.GankPractice;

import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 */
public class GankDataModel implements I_DataModel {
    private static final GankDataModel INSTANCE = new GankDataModel();

    private GankDataModel(){}

    public static GankDataModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<GankBean> getData(String type, int size, int page) {
        return GankPractice.getInstance().getGankService().getData(type, size, page);
    }
}
