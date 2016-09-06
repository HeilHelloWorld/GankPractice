package com.example.lichu.gankpractice.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lichu.gankpractice.R;
import com.example.lichu.gankpractice.widget.MultiSwipeRefreshLayout;

import butterknife.Bind;

/**
 * Usage:
 * Created: lichu on 2016/9/3.
 */
public abstract class MySwipeRefreshLayoutActivity extends MyToolbarActivity {
    @Bind(R.id.multi_swipe_refresh_layout) protected MultiSwipeRefreshLayout mMultiSwipeRefreshLayout;

    private boolean refreshStatus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.initMultiSwipeRefreshLayout();
    }

    private void initMultiSwipeRefreshLayout() {
        mMultiSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mMultiSwipeRefreshLayout.setOnRefreshListener(() -> onSwipeRefresh());
    }

    protected abstract void onSwipeRefresh();

    public void setRefreshStatus(boolean refreshStatus) {
        this.refreshStatus = refreshStatus;
    }

    public boolean isRefreshStatus() {
        return refreshStatus;
    }

    public void refresh(final boolean refresh) {
        if (mMultiSwipeRefreshLayout == null) return;

        if (!refresh && refreshStatus) {
            mMultiSwipeRefreshLayout.postDelayed(() -> {
                MySwipeRefreshLayoutActivity.this.mMultiSwipeRefreshLayout.setRefreshing(false);
                MySwipeRefreshLayoutActivity.this.refreshStatus = false;
            }, 1500);
        } else if (!this.refreshStatus) {
            mMultiSwipeRefreshLayout.post(() -> {
                MySwipeRefreshLayoutActivity.this.mMultiSwipeRefreshLayout.setRefreshing(true);
            });
            this.refreshStatus = true;
        }

    }
}
