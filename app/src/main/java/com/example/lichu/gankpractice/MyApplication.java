package com.example.lichu.gankpractice;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.anupcowkur.reservoir.Reservoir;
import com.example.lichu.gankpractice.gank.GankApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

/**
 * Created by lichu on 2016/8/29.
 * do some initial works here
 */
public class MyApplication extends Application {
    private static MyApplication application = new MyApplication();
    public boolean log = true;
    public Gson mGson;

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.init();
        this.initGson();
        this.initReservoizr();
    }

    private void initReservoizr() {
        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE, this.mGson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initGson() {
        this.mGson = new GsonBuilder().setDateFormat(GankApi.GANK_DATA_FORMAT).create();
    }
}
