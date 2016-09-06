package com.example.lichu.gankpractice.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lichu.gankpractice.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Usage:
 * Created: lichu on 2016/9/5.
 */
public abstract class MyDrawerLayoutActivity extends MySwipeRefreshLayoutActivity {

    @Bind(R.id.root_view) protected DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view) protected NavigationView mNavigationView;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    protected HashMap<Integer, MenuItem> mMenuItemHashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getNavigationItemSelectedListener() != null) {
            this.mNavigationView.setNavigationItemSelectedListener(this.getNavigationItemSelectedListener());
        }

        this.mDrawerLayout.addDrawerListener(new MyDrawerListener());

        this.mMenuItemHashMap = new HashMap<>();
        int[] menuItemIds = this.getMenuItemIds();
        if (menuItemIds.length > 0) {
            for (int id : menuItemIds) {
                this.mMenuItemHashMap.put(id, this.mNavigationView.getMenu().findItem(id));
            }
        }

        this.mActionBarDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, R.string.app_name, R.string.app_menu);
    }

    protected abstract int[] getMenuItemIds();

    protected abstract NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener();

    protected abstract void onMenuItemClick(MenuItem now);

    protected boolean menuItemChecked(int itemId) {
        MenuItem old = null;
        MenuItem now;

        if (this.mMenuItemHashMap.containsKey(itemId)) {
            for (Map.Entry<Integer, MenuItem> entry : this.mMenuItemHashMap.entrySet()) {
                MenuItem menuItem = entry.getValue();

                if (menuItem.isChecked()) {
                    old = menuItem;
                }

                if (old != null && old.getItemId() == itemId) {
                    break;
                }

                if (menuItem.getItemId() == itemId) {
                    now = menuItem;
                    menuItem.setChecked(true);
                    this.onMenuItemClick(now);
                } else {
                    menuItem.setChecked(false);
                }
            }
            this.mDrawerLayout.closeDrawer(mNavigationView);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.mDrawerLayout.isDrawerOpen(mNavigationView)) {
            this.mDrawerLayout.closeDrawer(mNavigationView);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mActionBarDrawerToggle.syncState();
    }

    private class MyDrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            MyDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            MyDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerOpened(drawerView);
            if (MyDrawerLayoutActivity.this.mActionBarHelper != null) {
                MyDrawerLayoutActivity.this.mActionBarHelper.onDrawerOpened();
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            MyDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerClosed(drawerView);
            MyDrawerLayoutActivity.this.mActionBarHelper.onDrawerClosed();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            MyDrawerLayoutActivity.this.mActionBarDrawerToggle.onDrawerStateChanged(newState);
        }
    }
}
