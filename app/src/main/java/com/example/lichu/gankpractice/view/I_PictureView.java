package com.example.lichu.gankpractice.view;

import android.net.Uri;

/**
 * Created by lichu on 2016/8/29.
 */
public interface I_PictureView extends I_BasicView {

    void onDownloadSuccess(String path);

    void onShare(Uri uri);
}
