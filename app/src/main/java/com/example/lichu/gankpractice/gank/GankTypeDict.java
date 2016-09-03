package com.example.lichu.gankpractice.gank;

import java.util.HashMap;
import java.util.IdentityHashMap;

/**
 * Usage:
 * Created: lichu on 2016/8/29.
 */
public class GankTypeDict {
    public static final int DONT_SWITCH = -1;
    public static final HashMap<Integer, String> type2Url  = new HashMap<>();

    static {
        type2Url.put(GankType.DAILY, GankApi.DATA_TYPE_ALL);
        type2Url.put(GankType.ANDROID, GankApi.DATA_TYPE_ANDROID);
        type2Url.put(GankType.APP, GankApi.DATA_TYPE_APP);
        type2Url.put(GankType.IOS, GankApi.DATA_TYPE_IOS);
        type2Url.put(GankType.JS, GankApi.DATA_TYPE_JS);
        type2Url.put(GankType.WELFRAE, GankApi.DATA_TYPE_WELFARE);
        type2Url.put(GankType.RECOMMEND, GankApi.DATA_TYPE_RECOMMEND);
        type2Url.put(GankType.RESOURCES, GankApi.DATA_TYPE_EXTEND_RESOURCES);
    }
}
