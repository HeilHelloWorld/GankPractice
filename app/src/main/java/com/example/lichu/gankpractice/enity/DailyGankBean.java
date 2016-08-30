package com.example.lichu.gankpractice.enity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lichu on 2016/8/28.
 *
 * DailyData from Gank.io
 * http://gank.io/api/day/2015/08/07
 */
public class DailyGankBean extends Error implements Serializable {
    @SerializedName("results") public DailyResults results;
    @SerializedName("category") public ArrayList<String> category;

    public class DailyResults {
        @SerializedName("福利") public ArrayList<BasicGankBean> welfareData;
        @SerializedName("Android") public ArrayList<BasicGankBean> androidData;
        @SerializedName("IOS") public ArrayList<BasicGankBean> iosData;
        @SerializedName("前端") public ArrayList<BasicGankBean> jsData;
        @SerializedName("休息视频") public ArrayList<BasicGankBean> videoData;
        @SerializedName("拓展资源") public ArrayList<BasicGankBean> resourcesData;
        @SerializedName("App") public ArrayList<BasicGankBean> appData;
        @SerializedName("瞎推荐") public ArrayList<BasicGankBean> recommendData;
    }

}
