package com.example.lichu.gankpractice.utils;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirDeleteCallback;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.lichu.gankpractice.utils.rx.RxEvent;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Usage: for caches data in disk
 * Created: lichu on 2016/8/29.
 */
public class ReservoirUtils {
    private static final String TAG = "ReservoirUtils";
    private static final ReservoirUtils INSTANCE = new ReservoirUtils();

    private ReservoirUtils() {}
    public static ReservoirUtils getInstance() {
        return INSTANCE;
    }

    public void put(final String key,final Object object) {
        if (object == null) {
            return;
        }
        Reservoir.putAsync(key, object, new ReservoirPutCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Put success: key=" + key + "object" + object.getClass());
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }

    public boolean contains(String key) {
        try {
            return Reservoir.contains(key);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public void refresh(final String key, final Object object) {
        if (this.contains(key)) {
            Reservoir.deleteAsync(key, new ReservoirDeleteCallback() {
                @Override
                public void onSuccess() {
                    ReservoirUtils.this.put(key, object);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            });
        } else {
            this.put(key, object);
        }
    }

    public <T> Observable<T> get(String key, Class<T> _class) {
        return Reservoir.getAsync(key, _class);
    }

    public <T> Observable<T> get(Class<T> _class) {
        String key = _class.getSimpleName();
        return this.get(key, _class);
    }

    public <T> void get(String key, Type type, final ReservoirGetCallback<T> callback) {
        Reservoir.getAsync(key, type, callback);
    }
}
