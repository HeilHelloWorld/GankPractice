package com.example.lichu.gankpractice.utils;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Usage:
 * Created: lichu on 2016/8/30.
 */
public class RxUtils {
    private static Observable.Transformer ioToMainThreadTransformer;

    static {
        ioToMainThreadTransformer = createIoToMainTransformer();
    }

    private static <T> Observable.Transformer<T, T> createIoToMainTransformer() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                                         .unsubscribeOn(Schedulers.computation())
                                         .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyIoToMainSchedulers() {
        return ioToMainThreadTransformer;
    }
}
