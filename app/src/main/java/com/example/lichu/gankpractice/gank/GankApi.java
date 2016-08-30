package com.example.lichu.gankpractice.gank;

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
public class GankApi {
    public static final String GANK_HOME_PAGE_NAME = "干货集中营";
    public static final String GANK_HOME_PAGE_URL = "http://gank.io/";

    public static final String BASE_URL = "http://gank.io/api/";

    public static final String DATA_TYPE_WELFARE = "福利";
    public static final String DATA_TYPE_ANDROID = "Android";
    public static final String DATA_TYPE_IOS = "iOS";
    public static final String DATA_TYPE_REST_VIDEO = "休息视频";
    public static final String DATA_TYPE_EXTEND_RESOURCES = "拓展资源";
    public static final String DATA_TYPE_JS = "前端";
    public static final String DATA_TYPE_APP = "App";
    public static final String DATA_TYPE_RECOMMEND = "瞎推荐";
    public static final String DATA_TYPE_ALL = "all";

    public static final int DEFAULT_DATA_SIZE = 10;
    public static final int DEFAULT_DAILY_SIZE = 15;

    public static final String GANK_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}
