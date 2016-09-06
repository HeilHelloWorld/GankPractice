package com.example.lichu.gankpractice.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.orhanobut.logger.Logger;

/**
 * Usage:
 * Created: lichu on 2016/9/5.
 */
public class GlideUtil {
    private static final String TAG = "GlideUtils";

    public static void display(ImageView view, String url) {
        display(view, url, android.R.mipmap.sym_def_app_icon);
    }

    public static void display(ImageView view, String url, int sym_def_app_icon) {
        if (view == null) {
            Logger.e("GlideUtil.display's view is null");
            return;
        }
        Context context = view.getContext();

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(sym_def_app_icon)
                .crossFade()
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public static void displayNative(ImageView view, int resId) {
        if (view == null) {
            Logger.e("GlideUtil.display's view is null");
            return;
        }
        Context context = view.getContext();

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

       Glide.with(context)
               .load(resId)
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .crossFade()
               .centerCrop()
               .into(view)
               .getSize((width, height) -> {
                   if (!view.isShown()) {
                       view.setVisibility(View.VISIBLE);
                   }
               });
    }
}
