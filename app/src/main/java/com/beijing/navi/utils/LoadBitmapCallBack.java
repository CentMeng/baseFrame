package com.beijing.navi.utils;

import android.graphics.Bitmap;

/**
 * Created by qiuzhenhuan on 16/6/1.
 */
public interface LoadBitmapCallBack {
    void onSuccess(Bitmap bitmap);
    void onError(Exception e);
}
