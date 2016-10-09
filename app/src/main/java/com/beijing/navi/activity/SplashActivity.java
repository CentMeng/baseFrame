package com.beijing.navi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.core.utils.Text.StringUtils;
import com.android.core.utils.phone.BaseTools;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;
import com.beijing.navi.runtimepermissions.PermissionsManager;
import com.beijing.navi.runtimepermissions.PermissionsResultAction;
import com.beijing.navi.service.UpdateImageService;
import com.beijing.navi.utils.VolleyHttpClient;
import com.core.api.event.request.ContextRequest;
import com.core.api.event.response.ContextResponse;
import com.google.gson.Gson;

import org.litepal.tablemanager.Connector;

import cache.DataCache;
import cache.DataCacheType;

/**
 * @author CentMeng
 *         <p/>
 *         启动页面
 */
public class SplashActivity extends BaseActivity {

    private boolean first; // 判断是否第一次打开软件
    private View view;
    private boolean contextOver = false, time = false, pushOver = false;



    private ImageView imv_splash;

    private TextView tv_jump;

    private Bitmap bitmap;

    /**
     * 是否已经跳转，防止多次跳转
     */
    private boolean isJump = false;

    /**
     * 是否展示广告图
     */
    private boolean isShowImage = false;

    /**
     * 应用市场
     */
    private ImageView img_bottom;



    //是否是推送进来
    private boolean isPush;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestQueue queue = Volley.newRequestQueue(this);
        view = View.inflate(this, R.layout.activity_splash, null);
        imv_splash = (ImageView) view.findViewById(R.id.imv_splash);
        tv_jump = (TextView) view.findViewById(R.id.tv_jump);
        img_bottom = (ImageView) view.findViewById(R.id.img_bottom);
        tv_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = true;
                contextOver = true;
                pushOver = true;
                startAc();
            }
        });
        getIntents();
        if (!isPush && !TextUtils.isEmpty(preferenceStorageService.getSplashImageFile()) && !TextUtils.isEmpty(preferenceStorageService.getSplashImage())) {
            if (preferenceStorageService.getSplashImageShow()) {
                bitmap = StringUtils.convertStringToIcon(preferenceStorageService.getSplashImageFile());
                if (bitmap != null) {
                    imv_splash.setImageBitmap(bitmap);
                    isShowImage = true;
                }
            }
        }
        setContentView(view);

        String umeng_channel = BaseTools.getAppMetaData(this, "UMENG_CHANNEL");
        if (!isShowImage) {
            if (umeng_channel.equals("c360")) {
                img_bottom.setVisibility(View.VISIBLE);
                img_bottom.setBackgroundResource(R.mipmap.ic_360);
            } else if (umeng_channel.equals("xiaomi")) {
                img_bottom.setVisibility(View.VISIBLE);
                img_bottom.setBackgroundResource(R.mipmap.ic_xiaomi);
            } else if (umeng_channel.equals("huawei")) {
                img_bottom.setVisibility(View.VISIBLE);
                img_bottom.setBackgroundResource(R.mipmap.ic_huawei);
            } else if (umeng_channel.equals("pp")) {
                img_bottom.setVisibility(View.VISIBLE);
                img_bottom.setBackgroundResource(R.mipmap.ic_pp);
            } else if (umeng_channel.equals("lenovo")) {
                img_bottom.setVisibility(View.VISIBLE);
                img_bottom.setBackgroundResource(R.mipmap.ic_lenovo);
            } else {
                img_bottom.setVisibility(View.GONE);
            }
        }
        /**
         * 创建数据库
         */
        SQLiteDatabase db = Connector.getDatabase();

        // runtime permission for android 6.0, just require all permissions here for simple
        requestPermissions();
    }


    private void getIntents() {
        Intent intent = this.getIntent();

            pushOver = true;
            startAc();
    }

    private Thread contextThread;

    private void getAllInfomation() {
        getContext();
        if (isPush || !isShowImage || preferenceStorageService.isFirst()) {
            contextThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        time = true;
                        startAc();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        time = true;
                        startAc();
                    }
                }
            });
            contextThread.start();
        } else {

            CountDownTimer timer = new CountDownTimer(4000, 1000) {
                @Override
                public void onTick(long l) {
                    tv_jump.setText(String.format("跳过:%d秒", l / 1000));
                    if (l / 1000 == 1) {
                        contextOver = true;
                        //非push情况，所以push不用考虑
                        pushOver = true;
                        time = true;
                        onFinish();
                        startAc();
                    }
                }

                @Override
                public void onFinish() {

                }
            };
            timer.start();
            tv_jump.setVisibility(View.VISIBLE);
        }
    }

    private void getContext() {

        /**
         * 历史搜索记录
         */
        String histroy = DataCache.getDataCache().queryCache("histroy");
        if (!TextUtils.isEmpty(histroy)) {
            app.setSearchHistroy(histroy.split(","));
        }

        ContextRequest request = new ContextRequest();
        request.setListener(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                app.setParams(response);
                contextOver = true;
                saveImage(response);
                startAc();
            }
        });
        request.setErrorlistener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                contextOver = true;
                startAc();
            }
        });
        volleyHttpClient.doNetTask(VolleyHttpClient.GETSTRING, request, DataCacheType.CACHE);

    }

    private void saveImage(String json) {
        Gson gson = new Gson();
        ContextResponse response = gson.fromJson(json, ContextResponse.class);
        preferenceStorageService.setSplashImageShow(response.getParam().getMobileScreenConfig().isShowSplashScreen());
        Intent intent = new Intent(this, UpdateImageService.class);
        intent.putExtra("url", response.getParam().getMobileScreenConfig().getImageUrl());
        intent.putExtra("preUrl", preferenceStorageService.getSplashImage());
        startService(intent);
    }

    private void startAc() {
        if (contextOver && time && pushOver) {
            if (preferenceStorageService.isFirst()) {
                preferenceStorageService.setFirst();
                startActivity(new Intent(this, GuidePageActivity.class));
                time = false;
                contextOver = false;
                pushOver = false;
            } else {

            }
            time = false;
            contextOver = false;
            pushOver = false;
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // 进入主程序的方法
//    public void into() {
    // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
//        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        // 给view设置动画效果
//        view.startAnimation(animation);
//        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
//        animation.setDuration(1500);
//        view.startAnimation(animation);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycle();
        System.gc();
    }

    private void recycle() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (contextThread != null && !contextThread.isInterrupted()) {
            contextThread.interrupt();
        }
    }

    Response.Listener<com.core.api.event.response.Response> tokenListener = new Response.Listener<com.core.api.event.response.Response>() {
        @Override
        public void onResponse(com.core.api.event.response.Response response) {
            if (response.isError()) {
                if (response.getCode() == 208) {
                    app.setUserParam(null);
                    app.setStatisticsParam(null);
                }
            }
        }
    };



    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
