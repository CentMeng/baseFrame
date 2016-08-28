package com.core.api.utils.cache;


import android.graphics.Bitmap;

/**
 * DiskLruCache 的配置文件
 * @author mengxc （csdn@vip.163.com）
 *
 */
public class DiskCacheParams {

    public final static String DIR = "picture";

    public final static int DEFAULT_DISKCACHE_SIZE = 20 * 1024 * 1024;

    public final static Bitmap.CompressFormat COMPRESSFORMAT = Bitmap.CompressFormat.PNG ;

    public final static int QUALITY = 100;

}