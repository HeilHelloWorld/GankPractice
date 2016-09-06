package com.example.lichu.gankpractice.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Usage:
 * Created: lichu on 2016/9/2.
 */
public class DeviceUtil {
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isSDAavilable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String createAppFolder(String appName, Application application) {
        File root;
        File folder;

        if (DeviceUtil.isSDAavilable()) {
            root = Environment.getExternalStorageDirectory();
                folder = new File(root, appName);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

        } else {
            root = application.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }
}
