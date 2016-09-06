package com.example.lichu.gankpractice.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import com.example.lichu.gankpractice.R;

import butterknife.Bind;

/**
 * Usage:
 * Created: lichu on 2016/9/2.
 */
public abstract class MyToolbarActivity extends MyAppCompatActivity{
    @Bind(R.id.toolbar) protected Toolbar mToolbar;
    @Bind(R.id.toolbar_include) protected AppBarLayout mAppBarLayout;

    protected ActionBarHelper mActionBarHelper;

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showBack() {
        if (this.mActionBarHelper != null) {
            this.mActionBarHelper.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setBarVisibility(boolean visibility) {
        if (visibility) {
            this.mAppBarLayout.setVisibility(View.VISIBLE);
        } else {
            this.mAppBarLayout.setVisibility(View.GONE);
        }
    }

    protected void initToolbarHelper() {
        if (this.mToolbar == null || this.mAppBarLayout == null) {
            return;
        }
        this.setSupportActionBar(this.mToolbar);
        this.mActionBarHelper = this.createActionBarHelper();
        this.mActionBarHelper.init();
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(6.0f);
        }
    }



    private ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }




    // 协助修改标题
    public class ActionBarHelper {
        private final ActionBar mActionBar;
        public CharSequence mDrawerTitle;
        public CharSequence mTitle;


        public ActionBarHelper() {
            this.mActionBar = getSupportActionBar();
        }

        public void init() {
            if (mActionBar == null) {
                return;
            }
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setDisplayShowHomeEnabled(false);
            this.mTitle = mDrawerTitle = getTitle();
        }

        public void onDrawerClosed() {
            if (this.mActionBar == null) {
                return;
            }
            this.mActionBar.setTitle(this.mTitle);
        }

        public void onDrawerOpened() {
            if (this.mActionBar == null) {
                return;
            }
            this.mActionBar.setTitle(this.mDrawerTitle);
        }

        public void setTitle(CharSequence title) {
            this.mTitle = title;
        }


        public void setDrawerTitle(CharSequence drawerTitle) {
            this.mDrawerTitle = drawerTitle;
        }


        public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
            if (this.mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }
}
