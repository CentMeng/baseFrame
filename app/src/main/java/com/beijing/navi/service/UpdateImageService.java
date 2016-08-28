package com.beijing.navi.service;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

import com.android.core.constant.LogConstant;
import com.android.core.utils.LogUtils;
import com.android.core.utils.SharePreferenceStorageService;
import com.android.core.utils.Text.StringUtils;
import com.beijing.navi.utils.ImageUtils;

/**
 * @author CentMeng csdn@vip.163.com on 16/3/1.
 */
public class UpdateImageService extends IntentService {


    public UpdateImageService() {
        super("UpdateImageService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            //之前存储的url
            String preUrL = intent.getStringExtra("preUrl");
            try {
                //之前下载的图片与现在图片不同，执行下载
                if(url == null){
                    url = "";
                }
                if (!url.equals(preUrL)) {
                    loadPicture(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private void loadPicture(String url) throws Exception {
//        HttpDownloader downloader = new HttpDownloader();
//        String result = downloader.downloadFile(url, "zhidian/", "start.jpg", true);
        String result = StringUtils.convertIconToString(ImageUtils.getHttpBitmap(url));
        if (!TextUtils.isEmpty(result)) {
            //图片下载成功则存储路径，以后如果是重复路径则不下载
            LogUtils.e(LogConstant.RESULT_OPERA, "启动图片下载成功保存图片");
            SharePreferenceStorageService preferenceStorageService = SharePreferenceStorageService.newInstance(getApplicationContext());
            preferenceStorageService.setSplashImage(url,result);
        } else {
            LogUtils.e(LogConstant.RESULT_OPERA, "启动图片下载失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(LogConstant.RESULT_OPERA, "关闭UpdateImageService服务");
    }
}
