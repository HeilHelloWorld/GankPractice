package com.example.lichu.gankpractice.presenter;

import android.text.format.DateUtils;

import com.example.lichu.gankpractice.gank.GankApi;
import com.example.lichu.gankpractice.gank.GankTypeDict;
import com.example.lichu.gankpractice.utils.DateUtil;
import com.example.lichu.gankpractice.utils.ReservoirUtils;
import com.example.lichu.gankpractice.view.I_DataView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * usage: presenter for data page
 * Created by lichu on 2016/8/29.
 */
public class DataPresenter extends BasicPresenter<I_DataView> {

    private EasyDate mCurrentDate;
    private int mPage;

    private ReservoirUtils mReservoirUtils;

    public DataPresenter() {
        this.mReservoirUtils = ReservoirUtils.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        this.mCurrentDate = new EasyDate(calendar);
        this.mPage = 1;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public int getPage(int page) {
        return this.mPage;
    }

    public void getDaily(final boolean refresh, final int oldPage) {
        if (oldPage != GankTypeDict.DONT_SWITCH) {
            this.mPage = 1;
        }
        this.mCompositeSubscription.add(this.mDataManager);
    }

    public class EasyDate implements Serializable {
        private Calendar mCalendar;
        public EasyDate (Calendar calendar) {
            this.mCalendar = calendar;
        }

        public int getYear() {
            return mCalendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            return mCalendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return mCalendar.get(Calendar.DAY_OF_MONTH);
        }

        public List<EasyDate> getPastTime() {
            List<EasyDate> dates = new ArrayList<>();
            for (int i = 0; i < GankApi.DEFAULT_DAILY_SIZE; i++) {

                //TODO something still remains to be settled
                long time = this.mCalendar.getTimeInMillis() - ((mPage - 1) * GankApi.DEFAULT_DAILY_SIZE * DateUtil.ONE_DAY) - i * DateUtil.ONE_DAY;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                EasyDate date = new EasyDate(calendar);
                dates.add(date);
            }
            return dates;
        }
    }
}
