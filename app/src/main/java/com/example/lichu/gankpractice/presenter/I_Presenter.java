package com.example.lichu.gankpractice.presenter;

import com.example.lichu.gankpractice.view.I_BasicView;

/**
 * Created by lichu on 2016/8/29.
 */
public interface I_Presenter<V extends I_BasicView> {
    void attachView(V view);
    void detachView();
}
