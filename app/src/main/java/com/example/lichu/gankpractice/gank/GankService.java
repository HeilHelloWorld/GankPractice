package com.example.lichu.gankpractice.gank;

import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.enity.GankBean;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by lichu on 2016/8/29.
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
 * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
 * 请求个数： 数字，大于0
 * 第几页：数字，大于0
 * 例：
 * http://gank.io/api/data/Android/10/1
 * http://gank.io/api/data/福利/10/1
 * http://gank.io/api/data/iOS/20/2
 * http://gank.io/api/data/all/20/2
 * 每日数据： http://gank.io/api/day/年/月/日
 * 例：
 * http://gank.io/api/day/2015/08/06
 */
public interface GankService {
    @GET("data/{type}/{size}/{page}")
    Observable<GankBean> getData(@Path("type") String type,@Path("size") int size,@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<DailyGankBean> getDaily(@Path("year") int year,@Path("month") int month,@Path("day") int day);
}
