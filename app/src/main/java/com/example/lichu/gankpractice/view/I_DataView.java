package com.example.lichu.gankpractice.view;

import com.example.lichu.gankpractice.enity.BasicGankBean;
import com.example.lichu.gankpractice.enity.DailyGankBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichu on 2016/8/29.
 */
public interface I_DataView extends I_BasicView {
    /**
     * 查询 每日干货 成功
     *
     * @param dailyData dailyData
     * @param refresh 是否刷新
     */
    void onGetDailySuccess(List<DailyGankBean> dailyData, boolean refresh);

    /**
     * 查询 ( Android、iOS、前端、拓展资源、福利、休息视频 ) 成功
     *
     * @param data data
     * @param refresh 是否刷新
     */
    void onGetDataSuccess(List<BasicGankBean> data, boolean refresh);

    /**
     * 切换数据源成功
     *
     * @param type type
     */
    void onSwitchSuccess(int type);

    /**
     * 获取每日详情数据
     *
     * @param title title
     * @param detail detail
     */
    void getDailyDetail(String title, ArrayList<ArrayList<BasicGankBean>> detail);
}
