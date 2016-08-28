package com.core.api.utils.cache;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BitmapCache implements ImageCache {

    private LruCache<String, Bitmap> memoryCache;

    private DiskLruCache diskCache;

    public BitmapCache(DiskLruCache diskCache, LruCache<String, Bitmap> memoryCache) {
        this.memoryCache = memoryCache;
        this.diskCache = diskCache;
    }


    @Override
    public Bitmap getBitmap(String url) {
        String key = getMD5(url);
        if (memoryCache.get(key) != null) {
            return memoryCache.get(key);
        } else {
            DiskLruCache.Snapshot snapshot = null;
            try {
                snapshot = diskCache.get(key);
                if (snapshot != null) {
                    InputStream is = snapshot.getInputStream(0);
                    return BitmapFactory.decodeStream(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void putBitmap(String url, final Bitmap bitmap) {
        final String key = getMD5(url);
        memoryCache.put(key, bitmap);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DiskLruCache.Editor editor = diskCache.edit(key);
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (bitmap.compress(DiskCacheParams.COMPRESSFORMAT, DiskCacheParams.QUALITY, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                        diskCache.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.substring(8, 24).toString();
    }

}