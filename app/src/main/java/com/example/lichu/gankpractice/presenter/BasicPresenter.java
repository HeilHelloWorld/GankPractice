package com.example.lichu.gankpractice.presenter;

import com.example.lichu.gankpractice.model.DataManager;
import com.example.lichu.gankpractice.view.I_BasicView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lichu on 2016/8/29.
 */
public class BasicPresenter<V extends I_BasicView> implements I_Presenter<V> {

    private V mView;
    public CompositeSubscription mCompositeSubscription;
    public DataManager mDataManager;

    @Override
    public void attachView(V view) {
        this.mView = view;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mDataManager = DataManager.getInstance();
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
        this.mDataManager = null;
    }

    public boolean isViewAttached() {
        return mView == null;
    }

    public V getView() {
        return mView;
    }


}
