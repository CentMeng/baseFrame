package com.beijing.navi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import org.androidannotations.api.SdkVersionHelper;

/**
 * @author CentMeng csdn@vip.163.com on 16/7/13.
 */
public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        try{
            if(SdkVersionHelper.getSdkInt()<21){
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                if(imagePipeline != null){
                    imagePipeline.clearMemoryCaches();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }  //如果无网络连接activeInfo为null

}