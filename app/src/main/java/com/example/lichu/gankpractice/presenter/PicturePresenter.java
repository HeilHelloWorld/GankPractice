package com.example.lichu.gankpractice.presenter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.lichu.gankpractice.R;
import com.example.lichu.gankpractice.utils.DeviceUtil;
import com.example.lichu.gankpractice.utils.RxUtils;
import com.example.lichu.gankpractice.view.I_PictureView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Usage:
 * Created: lichu on 2016/9/1.
 */
public class PicturePresenter extends BasicPresenter<I_PictureView> {
    public Observable<String> getSavedPictureObservable(GlideBitmapDrawable glideBitmapDrawable, Context context, Application application) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String dirPath = DeviceUtil.createAppFolder(context.getString(R.string.app_name), application);
                File downloadFile = new File(new File(dirPath), UUID.randomUUID().toString().replace("-", "") + ".jpg");
                if (!downloadFile.exists()) {
                    downloadFile.mkdirs();
                }

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);
                    glideBitmapDrawable.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.fromFile(downloadFile);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                context.sendBroadcast(intent);
                subscriber.onNext(downloadFile.getPath());
            }
        }).compose(RxUtils.applyIoToMainSchedulers());
    }

    public void downloadPicture(GlideBitmapDrawable wrapper, Context context, Application application){
        Subscription s = this.getSavedPictureObservable(wrapper, context, application).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                PicturePresenter.this.mCompositeSubscription.remove(this);
            }

            @Override
            public void onError(Throwable e) {
                if (PicturePresenter.this.getView() != null) {
                    PicturePresenter.this.getView().onFailure(e);
                }
            }

            @Override
            public void onNext(String s) {
                if (PicturePresenter.this.getView() != null) {
                    PicturePresenter.this.getView().onDownloadSuccess(s);
                }
            }
        });
        this.mCompositeSubscription.add(s);
    }

}