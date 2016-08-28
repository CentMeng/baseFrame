package com.android.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.core.constant.SharePreConstant;
import com.android.core.utils.Text.JsonUtils;
import com.core.api.event.response.ContextResponse;
import com.core.api.event.response.param.ContextParam;
import com.google.gson.Gson;

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
     *  首页引导
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
    public long getMessageIntervalTime(){
        SharedPreferences preferences = getPreference(context);
        return preferences.getLong("messageIntervalTime", 0);
    }
    public void setMessageIntervalTime(){
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putLong("messageIntervalTime", new Date().getTime());
        editor.commit();
    }

    /**
     * 留言内容
     * @return
     */
    public String getMessageContent(){
        SharedPreferences preferences = getPreference(context);
        return preferences.getString("messageContent", "");
    }
    public void setMessageContent(String messageContent){
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
    public void setTimePoint(){
        SharedPreferences preferences = getPreference(context);
        Editor editor = preferences.edit();
        editor.putLong("timepoint", new Date().getTime());
        editor.commit();
    }

    public long getTimePoint(){
        SharedPreferences preferences = getPreference(context);
        return preferences.getLong("timepoint", 0);
    }


    private String contextTxt = "{\"code\":0,\"msg\":\"成功\",\"param\":{\"YearOfService\":[{\"name\":\"YEAR_0_3\",\"value\":\"3年以下\"},{\"name\":\"YEAR_3_5\",\"value\":\"3-5年\"},{\"name\":\"YEAR_5_10\",\"value\":\"5-10年\"},{\"name\":\"YEAR_ABOVE_10\",\"value\":\"10年以上\"},{\"name\":\"InSchool\",\"value\":\"在读\"}],\"HotWord\":[\"教育\",\"留学\",\"出国申请\"],\"nickName\":{\"male\":[\"茅十八\",\"程灵素\",\"左子穆\",\"辛双清\",\"梅石坚\",\"令狐冲\",\"宋远桥\",\"俞莲舟\",\"俞岱岩\",\"张松溪\",\"张翠山\",\"殷梨亭\",\"莫声谷\",\"苏星河\",\"丁春秋\",\"梅超风\",\"高寄萍\",\"花满楼\",\"胡斐\",\"袁紫衣\",\"苗若兰\",\"程灵素\",\"雪山飞狐\",\"胡一刀\",\"苗人凤\",\"连城诀狄云\",\"水笙\",\"戚芳\",\"乔峰\",\"段誉\",\"虚竹\",\"慕容复\",\"阿朱\",\"王语嫣\",\"阿紫\",\"郭靖\",\"杨康\",\"黄蓉\",\"李文秀\",\"韦小宝\",\"沐剑屏\",\"方怡\",\"陈家洛\",\"霍青桐\",\"袁冠南\",\"杨过\",\"石破天\",\"石中玉\",\"张无忌\",\"袁承志\",\"马行空\",\"马春花\",\"徐铮\",\"商宝震\",\"何思豪\",\"阎基\",\"田归农\",\"南仁通\",\"补锅匠\",\"田青文\",\"陈家洛\",\"王铁匠\",\"石双英\",\"童怀道\",\"黄药师\",\"洪七公\",\"欧阳锋\",\"一灯\",\"林平之\"],\"female\":[\"香香公主\",\"小龙女\",\"任盈盈\",\"赵敏\",\"周芷若\",\"小昭\",\"殷离\",\"温青青\",\"阿九\",\"鸳鸯刀\",\"萧中慧\",\"萧半和\",\"阿青\",\"沐剑屏\",\"方怡\",\"建宁公主\",\"曾柔\",\"苏荃\",\"双儿\",\"阿珂\",\"温青青\",\"夏雪宜\"]},\"avatar\":{\"male\":[\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%95.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%951.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%952.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%953.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B5.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B51.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B52.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B53.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%92.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%921.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%922.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%923.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%86.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%861.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%862.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%863.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%85.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%851.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%852.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%853.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE2.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE3.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B8.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B81.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B82.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B83.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A11.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A12.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A13.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A6.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A61.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A62.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A63.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC2.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC3.png\"],\"famale\":[\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%95.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%951.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%952.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E4%BB%953.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B5.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B51.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B52.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%85%B53.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%92.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%921.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%922.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%8D%923.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%86.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%861.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%862.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B0%863.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%85.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%851.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%852.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E5%B8%853.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE2.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%82%AE3.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B8.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B81.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B82.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E7%9B%B83.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A11.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A12.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%B1%A13.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A6.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A61.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A62.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E8%BD%A63.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC1.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC2.png\",\"http://7xqxpm.com1.z0.glb.clouddn.com/%E9%A9%AC3.png\"]},\"CountryCode\":[{\"name\":\"61\",\"value\":\"澳大利亚\",\"code\":\"AU\"},{\"name\":\"49\",\"value\":\"德国\",\"code\":\"GE\"},{\"name\":\"7\",\"value\":\"俄罗斯\",\"code\":\"RU\"},{\"name\":\"33\",\"value\":\"法国\",\"code\":\"FR\"},{\"name\":\"1\",\"value\":\"加拿大\",\"code\":\"CA\"},{\"name\":\"82\",\"value\":\"韩国\",\"code\":\"KR\"},{\"name\":\"1\",\"value\":\"美国\",\"code\":\"US\"},{\"name\":\"60\",\"value\":\"马来西亚\",\"code\":\"MY\"},{\"name\":\"66\",\"value\":\"泰国\",\"code\":\"TH\"},{\"name\":\"886\",\"value\":\"台湾\",\"code\":\"TW\"},{\"name\":\"65\",\"value\":\"新加坡\",\"code\":\"SG\"},{\"name\":\"81\",\"value\":\"日本\",\"code\":\"JP\"},{\"name\":\"91\",\"value\":\"印度\",\"code\":\"IN\"},{\"name\":\"44\",\"value\":\"英国\",\"code\":\"GB\"},{\"name\":\"86\",\"value\":\"中国\",\"code\":\"CN\"},{\"name\":\"853\",\"value\":\"中国澳门特别行政区\",\"code\":\"MO\"},{\"name\":\"852\",\"value\":\"中国香港特别行政区\",\"code\":\"HK\"},{\"name\":\"41\",\"value\":\"瑞士\",\"code\":\"CH\"},{\"name\":\"353\",\"value\":\"爱尔兰\",\"code\":\"IE\"},{\"name\":\"46\",\"value\":\"瑞典\",\"code\":\"SE\"},{\"name\":\"43\",\"value\":\"奥地利\",\"code\":\"AT\"},{\"name\":\"31\",\"value\":\"荷兰\",\"code\":\"NL\"},{\"name\":\"358\",\"value\":\"芬兰\",\"code\":\"FI\"},{\"name\":\"32\",\"value\":\"比利时\",\"code\":\"BE\"},{\"name\":\"49\",\"value\":\"德国\",\"code\":\"DE\"},{\"name\":\"39\",\"value\":\"意大利\",\"code\":\"IT\"},{\"name\":\"34\",\"value\":\"西班牙\",\"code\":\"ES\"},{\"name\":\"351\",\"value\":\"葡萄牙\",\"code\":\"PT\"},{\"name\":\"45\",\"value\":\"丹麦\",\"code\":\"DK\"},{\"name\":\"47\",\"value\":\"挪威\",\"code\":\"NO\"},{\"name\":\"354\",\"value\":\"冰岛\",\"code\":\"IS\"},{\"name\":\"64\",\"value\":\"新西兰\",\"code\":\"NZ\"}],\"AppScoreConfig\":{\"ok\":\"去评分\",\"feedback\":\"吐槽\",\"cancel\":\"以后再说\",\"showFirstInstall\":false,\"showInSettings\":false,\"showFirstFinishAppointment\":false,\"description\":\"只要你一个眼神肯定我的爱就有意义\",\"uiType\":\"OneButton\"},\"motto\":\"您的教育咨询平台\",\"collegeCountry\":[{\"code\":\"CHINA\",\"name\":\"中国\",\"checked\":true,\"index\":0},{\"code\":\"US\",\"name\":\"美国\",\"checked\":true,\"index\":1},{\"code\":\"GB\",\"name\":\"英国\",\"checked\":true,\"index\":2},{\"code\":\"GE\",\"name\":\"德国\",\"checked\":true,\"index\":3},{\"code\":\"FR\",\"name\":\"法国\",\"checked\":true,\"index\":4},{\"code\":\"NL\",\"name\":\"荷兰\",\"checked\":true,\"index\":5}],\"articleTag\":[{\"code\":\"TRENDS\",\"name\":\"动态\",\"checked\":true,\"index\":0},{\"code\":\"EVERYBODY_SAYS\",\"name\":\"大家说\",\"checked\":true,\"index\":4},{\"code\":\"COLUMNS\",\"name\":\"专栏\",\"checked\":true,\"index\":5},{\"code\":\"STUDY_ABROAD\",\"name\":\"留学\",\"checked\":true,\"index\":1},{\"code\":\"ADVANCED_STUDY\",\"name\":\"考研\",\"checked\":true,\"index\":3},{\"code\":\"COLLEGE_ENTRANCE_EXAMINATION\",\"name\":\"高考\",\"checked\":true,\"index\":2}],\"homePageCard\":[{\"code\":\"NEWS\",\"name\":\"资讯\",\"index\":0,\"checked\":true},{\"code\":\"TOPIC\",\"name\":\"话题\",\"index\":1,\"checked\":true},{\"code\":\"COLUMN\",\"name\":\"专栏\",\"index\":2,\"checked\":true},{\"code\":\"HEADLINE\",\"name\":\"爆料\",\"index\":3,\"checked\":true}],\"mobileScreenConfig\":{\"url\":null,\"realm\":null,\"showSplashScreen\":false,\"imageUrl\":null}},\"success\":true}";

    public ContextParam getContextParam() {
        SharedPreferences preferences = getPreference(context);
        String json = preferences.getString("contextParam", contextTxt);
        LogUtils.e("--------------", json);
        if (TextUtils.isEmpty(json)) {
            json = contextTxt;
        }
        Gson gson = new Gson();
        ContextResponse response = gson.fromJson(json, ContextResponse.class);
        return response.getParam();

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


    public void setVideoToken(String token){
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(VIDEO_TOKEN, token);
        editor.commit();
    }

    public String getVideoToken(){
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(VIDEO_TOKEN,"");
    }

    public void setAudioToken(String token){
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putString(AUDIO_TOKEN, token);
        editor.commit();
    }

    public String getAudioToken(){
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(AUDIO_TOKEN,"");
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
