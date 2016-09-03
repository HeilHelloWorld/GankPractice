package com.example.lichu.gankpractice.presenter;

import android.provider.ContactsContract;
import android.text.format.DateUtils;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.example.lichu.gankpractice.constant.Constants;
import com.example.lichu.gankpractice.enity.BasicGankBean;
import com.example.lichu.gankpractice.enity.DailyGankBean;
import com.example.lichu.gankpractice.gank.GankApi;
import com.example.lichu.gankpractice.gank.GankType;
import com.example.lichu.gankpractice.gank.GankTypeDict;
import com.example.lichu.gankpractice.utils.DateUtil;
import com.example.lichu.gankpractice.utils.ReservoirUtils;
import com.example.lichu.gankpractice.view.I_DataView;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

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
        this.mCompositeSubscription.add(this.mDataManager.getDailyDataByNetwork(this.mCurrentDate)
            .subscribe(new Subscriber<List<DailyGankBean>>() {
                @Override
                public void onCompleted() {
                    if (DataPresenter.this.mCompositeSubscription != null) {
                        DataPresenter.this.mCompositeSubscription.remove(this);
                    }
                }

                // if the network is not available, then load data from disk
                @Override
                public void onError(Throwable e) {
                    try {
                        Logger.e(e.getMessage());
                    } catch (Throwable e1) {
                        Logger.e(e1.getMessage());
                    } finally {
                        if (refresh) {
                            Type resultType = new TypeToken<List<DailyGankBean>>() {}.getType();
                            DataPresenter.this.mReservoirUtils.get(GankType.DAILY + "", resultType, new ReservoirGetCallback<List<DailyGankBean>>() {
                                @Override
                                public void onSuccess(List<DailyGankBean> object) {
                                    if (oldPage != GankTypeDict.DONT_SWITCH) {
                                        if (DataPresenter.this.getView() != null) {
                                            DataPresenter.this.getView().onSwitchSuccess(GankType.DAILY);
                                        }
                                    }
                                    if (DataPresenter.this.getView() != null) {
                                        DataPresenter.this.getView().onGetDailySuccess(object, refresh);
                                    }
                                    if (DataPresenter.this.getView() != null) {
                                        DataPresenter.this.getView().onFailure(e);
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    DataPresenter.this.switchFailure(oldPage, e);
                                }
                            });
                        } else {
                            DataPresenter.this.getView().onFailure(e);
                        }
                    }
                }

                @Override
                public void onNext(List<DailyGankBean> dailyGankBeen) {
                    if (oldPage != GankTypeDict.DONT_SWITCH) {
                        if (DataPresenter.this.getView() != null) {
                            DataPresenter.this.getView().onSwitchSuccess(GankType.DAILY);
                        }
                    }

                    if (refresh) {
                        DataPresenter.this.mReservoirUtils.refresh(GankType.DAILY+"", dailyGankBeen);
                    }
                    if (DataPresenter.this.getView() != null) {
                        DataPresenter.this.getView().onGetDailySuccess(dailyGankBeen, refresh);
                    }
                }
            }));
    }

    public void getData(int type, boolean refresh, int oldPage) {
        if (oldPage != GankTypeDict.DONT_SWITCH) {
            this.mPage = 1;
        }
        String gankType = GankTypeDict.type2Url.get(type);
        if (gankType == null) {
            return;
        }
        Subscription s = this.mDataManager.getDataByNetwork(gankType, GankApi.DEFAULT_DATA_SIZE, this.mPage)
                .subscribe(new Subscriber<List<BasicGankBean>>() {
                    @Override
                    public void onCompleted() {
                        if (DataPresenter.this.mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            Logger.e(e.getMessage());
                        }
                        catch (Throwable e1) {
                            Logger.e(e.getMessage());
                        }
                        finally {
                            if (refresh) {
                                Type resultType = new TypeToken<ArrayList<BasicGankBean>>() {
                                }.getType();
                                DataPresenter.this.mReservoirUtils.get(type + "", resultType, new ReservoirGetCallback<ArrayList<BasicGankBean>>() {
                                    @Override
                                    public void onSuccess(ArrayList<BasicGankBean> object) {
                                        if (oldPage != GankTypeDict.DONT_SWITCH) {
                                            if (DataPresenter.this.getView() != null) {
                                                DataPresenter.this.getView().onSwitchSuccess(type);
                                            }
                                        }
                                        if (DataPresenter.this.getView() != null) {
                                            DataPresenter.this.getView().onGetDataSuccess(object, refresh);
                                            DataPresenter.this.getView().onFailure(e);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        DataPresenter.this.switchFailure(oldPage, e);
                                    }
                                });
                            }
                            else {
                                DataPresenter.this.getView().onFailure(e);
                            }
                        }
                    }

                    @Override
                    public void onNext(List<BasicGankBean> basicGankBeen) {
                        if (oldPage != GankTypeDict.DONT_SWITCH) {
                            if (DataPresenter.this.getView() != null) {
                                DataPresenter.this.getView().onSwitchSuccess(type);
                            }
                        }
                        if (refresh)
                            DataPresenter.this.mReservoirUtils.refresh(type+"", basicGankBeen);
                        if (DataPresenter.this.getView() != null) {
                            DataPresenter.this.getView().onGetDataSuccess(basicGankBeen, refresh);
                        }
                    }
                });
        this.mCompositeSubscription.add(s);
    }

    public void switchType(int type) {
        int oldPage = this.mPage;
        switch(type) {
            case GankType.DAILY :
                this.getDaily(true, oldPage);
                break;
            case GankType.ANDROID:
            case GankType.IOS:
            case GankType.JS:
            case GankType.RECOMMEND:
            case GankType.RESOURCES:
            case GankType.VIDEO:
            case GankType.APP:
            case GankType.WELFRAE:
                this.getData(type, true, oldPage);
        }
    }

    private void switchFailure(int oldPage, Exception e) {
        if (oldPage != GankTypeDict.DONT_SWITCH) {
            DataPresenter.this.mPage = oldPage;
        }
        if (DataPresenter.this.getView() != null) {
            DataPresenter.this.getView().onFailure(e);
        }
    }

    public void getDailyDetail(DailyGankBean.DailyResults dailyResults) {
        Subscription subscription = this.mDataManager.getDailyDetailByDailyResultss(dailyResults)
                .subscribe(new Subscriber<ArrayList<ArrayList<BasicGankBean>>>() {
                    @Override
                    public void onCompleted() {
                        if (DataPresenter.this.getView() != null) {
                            DataPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                            Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ArrayList<BasicGankBean>> arrayLists) {
                            if (DataPresenter.this.getView() != null) {
                                DataPresenter.this.getView().getDailyDetail(
                                        DateUtil.parseDateToString(dailyResults.welfareData.get(0).publishedAt.getTime(), Constants.DAILY_DATE_FORMAT),
                                        arrayLists);
                            }
                    }
                });
        this.mCompositeSubscription.add(subscription);
    }

}
