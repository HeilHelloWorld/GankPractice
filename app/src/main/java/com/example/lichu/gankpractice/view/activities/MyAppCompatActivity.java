package com.example.lichu.gankpractice.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.lichu.gankpractice.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Usage:
 * Created: lichu on 2016/9/2.
 */
public abstract class MyAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        
        this.initToolbar(savedInstanceState);
        this.initViews(savedInstanceState);
        this.initData();
        this.initListeners();
    }

    protected abstract void initListeners();

    protected abstract void initData();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract void initToolbar(Bundle savedInstanceState);

    /**
     * @param id
     * @param <V>
     * @return
     */
    @SuppressWarnings("uncheked")
    protected <V extends View> V findView(int id) {
        return (V) this.findViewById(id);
    }

    public void showToast(String msg) {
        this.showToast(msg, Toast.LENGTH_SHORT);
    }


    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, msg, duration);
        } else {
            ToastUtils.show(this, msg, ToastUtils.LENGTH_SHORT);
        }
    }


    public void showToast(int resId) {
        this.showToast(resId, Toast.LENGTH_SHORT);
    }


    public void showToast(int resId, int duration) {
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, resId, duration);
        } else {
            ToastUtils.show(this, resId, ToastUtils.LENGTH_SHORT);
        }
    }
}
