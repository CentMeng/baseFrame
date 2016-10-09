package com.android.core.utils.phone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.core.constant.SharePreConstant;
import com.android.core.utils.Text.JsonUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

/**
 * 用于全局变量检测是否第一次启动和登录信息
 */
public class SharePreferenceStorageService {

    private static SharePreferenceStorageService preferenceStorageService;
    private Context context;

    public final String RAWOFF = "rawoff";

    public final String SPLASH_IMAGE = "splashImage";

    public final String SPLASH_IMAGE_SHOW = "splashImageShow";

    public final String SPLASH_IMAGE_FILE = "splashImageFile";

    public final String VIDEO_TOKEN = "videoToken";

    public final String AUDIO_TOKEN = "audioToken";

    public final String PAYTYPE = "payType";

    public final String ISCHATPAYFIRST = "chatPayFirst";

    public final String APKINFO = "apkInfo";


    public SharePreferenceStorageService(Context context) {
        this.context = context;
    }

    SharedPreferences preferences;

    public static SharePreferenceStorageService newInstance(Context context) {

        if (preferenceStorageService == null) {
            preferenceStorageService = new SharePreferenceStorageService(
                    context);
        }

        return preferenceStorageService;
    }

    public SharedPreferences getPreference(Context context) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return preferences;
    }


    public String getApkInfo() {

        SharedPreferences preferences = getPreference(context);
        return preferences.getString(APKINFO, "");
    }

    public void setApkInfo(String apkInfo) {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString(APKINFO, apkInfo);
        editor.commit();
    }


    public String getLastPayType() {

        SharedPreferences preferences = getPreference(context);
        return preferences.getString(PAYTYPE, "Alipay");
    }

    public void setLastPayType(String payType) {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString(PAYTYPE, payType);
        editor.commit();
    }

    /**
     * @return
     */
    public boolean isFirst() {

        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean("isfirst", true);
    }

    public void setFirst() {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putBoolean("isfirst", false);
        editor.commit();
    }


    public boolean isChatPayFirst() {

        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean(ISCHATPAYFIRST, true);
    }

    public void setChatPayNoFirst() {

        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putBoolean(ISCHATPAYFIRST, false);
        editor.commit();
    }

    /**
     * 首页引导
     *
     * @return
     */
    public boolean isHomeFirst() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean("ishomefirst", true);
    }

    public void setHomeFirst() {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putBoolean("ishomefirst", false);
        editor.commit();
    }

    /**
     * 话题引导
     *
     * @return
     */
    public boolean isTopicFirst() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean("istopicfirst", true);
    }

    public void setTopicFirst() {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putBoolean("istopicfirst", false);
        editor.commit();
    }

    /**
     * 留言间隔时间
     */
    public long getMessageIntervalTime() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getLong("messageIntervalTime", 0);
    }

    public void setMessageIntervalTime() {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putLong("messageIntervalTime", new Date().getTime());
        editor.commit();
    }

    /**
     * 留言内容
     *
     * @return
     */
    public String getMessageContent() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getString("messageContent", "");
    }

    public void setMessageContent(String messageContent) {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString("messageContent", messageContent);
        editor.commit();
    }


    public void setContextParam(String json) {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString("contextParam", json);
        editor.commit();
    }

    /**
     * 引导 展示时间点
     */
    public void setTimePoint() {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putLong("timepoint", new Date().getTime());
        editor.commit();
    }

    public long getTimePoint() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getLong("timepoint", 0);
    }

    public void setSingleArticle(String articleId) {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString(SharePreConstant.SINGLE_ARTICLE, articleId);
        editor.commit();
    }

    public String getSingleArticle() {
        SharedPreferences preferences = getPreference(context);
        return preferences.getString(SharePreConstant.SINGLE_ARTICLE, "");

    }

    public void setQiniuToken(String token) {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putString("qiniuToken", token);
        editor.commit();
    }

    public String getQiniuToken() {
        SharedPreferences preferences = getPreference(context);
        String token = preferences.getString("qiniuToken", "");
        return token;
    }

    public void setLastRawOff(int rawOff) {
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putInt(RAWOFF, rawOff);
        editor.commit();
    }

    public int getLastRawOff() {
        SharedPreferences preferences = getPreference(context);
        int rawoff = preferences.getInt(RAWOFF, -100);
        return rawoff;
    }


    public void setMap(String json, String name) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(name, json);
        editor.commit();
    }

    public HashMap<String, String> getMap(String name) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String json = preferences.getString(name, "");
        if (!TextUtils.isEmpty(json)) {
            return JsonUtils.getMapForJson(json);
        } else {
            return new HashMap<String, String>();
        }
    }


    public void setMoneyBagBalance(float balance) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putFloat("moneybagbalance", balance);
        editor.commit();
    }

    public float getMoneyBagBalance() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getFloat("moneybagbalance", 0);
    }


    public void setSplashImage(String url, String file) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(SPLASH_IMAGE, url);
        editor.putString(SPLASH_IMAGE_FILE, file);
        editor.commit();
    }

    public String getSplashImage() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(SPLASH_IMAGE, "");
    }

    public String getSplashImageFile() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(SPLASH_IMAGE_FILE, "");
    }

    public void setSplashImageShow(boolean isShow) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(SPLASH_IMAGE_SHOW, isShow);
        editor.commit();
    }

    /**
     * 通过字符串获取数组
     *
     * @param key
     * @param baseData
     * @return
     */
    public String[] getArrayData(String key, String[] baseData) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String data_str = preferences.getString(key, "");
        if (!TextUtils.isEmpty(data_str)) {
            String[] dataArray = data_str.split(",---,");
            if (dataArray.length == baseData.length) {
                return dataArray;
            }
        }
        return baseData;
    }

    /**
     * 存储数组转化成字符串
     *
     * @param key
     * @param data
     */
    public void setArrayData(String key, String[] data) {
        String data_str = "";
        for (int i = 0; i < data.length; i++) {
            if (i != data.length - 1) {
                data_str += data[i] + ",---,";
            } else {
                data_str += data[i];
            }
        }
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(key, data_str);
        editor.commit();
    }

    /**
     * 是否使用保存下来的图片
     */
    public boolean getSplashImageShow() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getBoolean(SPLASH_IMAGE_SHOW, false);
    }


    public void setVideoToken(String token) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(VIDEO_TOKEN, token);
        editor.commit();
    }

    public String getVideoToken() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(VIDEO_TOKEN, "");
    }

    public void setAudioToken(String token) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(AUDIO_TOKEN, token);
        editor.commit();
    }

    public String getAudioToken() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(AUDIO_TOKEN, "");
    }


    /**
     * @param filename
     * @param data
     * @param <T>
     */
    public <T> void writeToFile(String filename, T... data) {
        // TODO Auto-generated method stub
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(context.getFilesDir().toString() + "/"
                            + filename));

            for (T list : data) {
                out.writeObject(list);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     * @param <T>
     * @return
     */
    public <T> Object getFromFile(String filename) {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(context.getFilesDir().toString() + "/"
                            + filename));
            Object data = inputStream.readObject();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

}
