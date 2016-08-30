package com.example.lichu.gankpractice.gank;

import com.example.lichu.gankpractice.MyApplication;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by lichu on 2016/8/29.
 */
public class GankPractice {
    private static final long CONNECTION_TIME_OUT = 5000L;
    private static GankPractice instance;
    private GankService mGankService;

    public static GankPractice getInstance() {
        if (instance == null) {
            instance = new GankPractice();
        }
        return instance;
    }

    private GankPractice() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS);

        if (MyApplication.getInstance().log) {
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    Logger.d(chain.request().urlString());
                    return response;
                }
            });
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(MyApplication.getInstance().mGson))
                .client(okHttpClient)
                .build();

        this.mGankService = retrofit.create(GankService.class);
    }

    public GankService getGankService() {
        return this.mGankService;
    }
}
