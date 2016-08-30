package com.example.lichu.gankpractice.utils.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Usage:
 * Created: lichu on 2016/8/29.
 */
public class RxBus {
    public static final RxBus INSTANCE = new RxBus();

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {}
    public static synchronized RxBus getInstance() {
        return INSTANCE;
    }

    public void send(RxEvent event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
