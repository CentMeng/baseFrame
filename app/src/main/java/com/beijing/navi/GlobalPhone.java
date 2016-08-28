package com.beijing.navi;

import android.app.NotificationManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.core.utils.SharePreferenceStorageService;
import com.android.core.utils.tools.MemorySpaceUtils;
import com.beijing.navi.utils.UploadUtil;
import com.core.api.event.response.param.ContextParam;
import com.core.api.event.response.param.StatisticsParam;
import com.core.api.event.response.param.UserParam;
import com.core.api.utils.FileUtils;
import com.google.gson.Gson;
import com.qiniu.android.storage.UploadManager;

import org.litepal.LitePalApplication;

import java.io.File;

import cache.DataCache;

/**
 * @author CentMeng csdn@vip.163.com on 15/9/8.
 */
public class GlobalPhone extends LitePalApplication {

    private static GlobalPhone instance;

    public static SharePreferenceStorageService preferenceStorageService;

    private StatisticsParam statisticsParam;

    private static ContextParam contextParam;

    private NotificationManager notificationManager;

    public static String token;

    public static GlobalPhone getInstance() {
        if (instance == null) {
            instance = new GlobalPhone();
        }
        return instance;
    }


    private boolean autoApkDownload = false;



    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        preferenceStorageService = SharePreferenceStorageService.newInstance(getApplicationContext());
        instance = this;
        contextParam = getContextParam();
        notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //极光推送设置标签
//        Set<String> tag = new HashSet<String>();
//        if (ApiSettings.debug) {
//            tag.add("android_test");
//        } else {
//            tag.add("android_production");
//        }
//        JPushInterface.setTags(this, tag, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//
//            }
//        });
//        JPushInterface.setDebugMode(ApiSettings.debug);    // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);            // 初始化 JPush

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private UploadManager uploadManager;

    private String uploadToken;

    private String videoUploadToken;

    private String audioUploadToken;


    public void setStatisticsParam(StatisticsParam statisticsParam) {
        this.statisticsParam = statisticsParam;
    }

    public StatisticsParam getStatisticsParam() {
        return statisticsParam;
    }

    private String[] searchHistroy;

    public String[] getSearchHistroy() {
        return searchHistroy;
    }

    public void setSearchHistroy(String[] searchHistroy) {
        this.searchHistroy = searchHistroy;
    }

    public UploadManager getUploadManager() {
        if (uploadManager == null) {
            uploadManager = UploadUtil.getUploadManager();
        }
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public String getUploadToken() {
        if (TextUtils.isEmpty(uploadToken)) {
            return preferenceStorageService.getQiniuToken();
        }
        return uploadToken;
    }

    public void setUploadToken(String uploadToken) {
        this.uploadToken = uploadToken;
        preferenceStorageService.setQiniuToken(uploadToken);
    }


    public String getVideoUploadToken() {
        if (TextUtils.isEmpty(videoUploadToken)) {
            return preferenceStorageService.getVideoToken();
        }
        return videoUploadToken;
    }

    public void setVideoUploadToken(String videoUploadToken) {
        this.videoUploadToken = videoUploadToken;
        preferenceStorageService.setVideoToken(videoUploadToken);
    }

    public String getAudioUploadToken() {
        if (TextUtils.isEmpty(audioUploadToken)) {
            return preferenceStorageService.getAudioToken();
        }
        return audioUploadToken;
    }

    public void setAudioUploadToken(String audioUploadToken) {
        this.audioUploadToken = audioUploadToken;
        preferenceStorageService.setAudioToken(videoUploadToken);
    }


    public void setParams(String json) {
        preferenceStorageService.setContextParam(json);
        //设置成null第一次启动会重新取
        contextParam = null;
    }


    UserParam userParam;

    public UserParam getUserParam() {
        if (userParam == null) {
            try {
                String gson = DataCache.getDataCache().queryCache("user_param");
                if (TextUtils.isEmpty(gson)) {
                    return null;
                }
                userParam = new Gson().fromJson(gson, UserParam.class);
                if (userParam == null || userParam.user == null) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }

        }

        return userParam;
    }

    public void setUserParam(UserParam userParam) {
        this.userParam = userParam;
        if (userParam == null) {
            token = "";
            DataCache.newInstance(this).saveToCache("access_token", "");
            DataCache.newInstance(this).saveToCache("user_param", "");
        } else {
            //必须保证有存储数据，所以如果没有空间，这时候要清除
            if (!MemorySpaceUtils.hasEnoughMemory(FileUtils.getDiskCachePath(this), 1024)) {
                DataCache.getDataCache().clearCache();
                DataCache.getDataCache().clearTempCache();
                if (!MemorySpaceUtils.hasEnoughMemory(FileUtils.getDiskCachePath(this), 1024)) {
                    com.android.core.utils.File.FileUtils.deleteDir(FileUtils.getDiskCacheDir(this, DataCache.dbName));
                    if (!MemorySpaceUtils.hasEnoughMemory(FileUtils.getDiskCachePath(this), 1024)) {
                        com.android.core.utils.File.FileUtils.deleteDir(FileUtils.getDiskCacheDir(this, DataCache.dbName));
                        if (!MemorySpaceUtils.hasEnoughMemory(FileUtils.getDiskCachePath(this), 1024)) {
                            com.android.core.utils.File.FileUtils.deleteDir(new File(FileUtils.getDiskCachePath(this)));
                        }
                    }
                    DataCache.setDataCache(null);
                }
            }
            DataCache.newInstance(this).saveToCache("access_token", userParam.token);
            DataCache.newInstance(this).saveToCache("user_param", new Gson().toJson(userParam));
            token = userParam.token;
        }


    }


    private boolean isDownload;

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }


    public boolean isAutoApkDownload() {
        return autoApkDownload;
    }

    public void setAutoApkDownload(boolean autoApkDownload) {
        this.autoApkDownload = autoApkDownload;
    }


    public static ContextParam getContextParam() {
        if (contextParam != null) {
            return contextParam;
        } else {
            contextParam = preferenceStorageService.getContextParam();
            return contextParam;
        }
    }


}
