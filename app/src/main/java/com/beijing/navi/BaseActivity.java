package com.beijing.navi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.utils.File.FileUtils;
import com.android.core.utils.Toast.ToastUtil;
import com.android.core.utils.Toast.ToastUtilHaveRight;
import com.android.core.utils.phone.BaseTools;
import com.android.core.utils.phone.OnClickUtil;
import com.android.core.utils.phone.SharePreferenceStorageService;
import com.android.volley.VolleyError;
import com.beijing.navi.activity.WebViewActivity;
import com.beijing.navi.receiver.RetryLoginReceiver;
import com.beijing.navi.utils.ActivityCollector;
import com.beijing.navi.utils.GlobalWlanErrorListener;
import com.beijing.navi.utils.UploadUtil;
import com.beijing.navi.utils.VolleyHttpClient;
import com.beijing.navi.utils.image.ConfigConstants;
import com.beijing.navi.view.PrompfDialog;
import com.beijing.navi.view.wheelview.ArrayWheelAdapter;
import com.beijing.navi.view.wheelview.OnWheelChangedListener;
import com.beijing.navi.view.wheelview.WheelView;
import com.core.api.ApiSettings;
import com.core.api.common.HttpService;
import com.core.api.event.ApiResponse;
import com.core.api.utils.cache.BitmapCache;
import com.core.api.utils.cache.DiskCacheParams;
import com.core.api.utils.cache.DiskLruCache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.qiniu.android.storage.UploadManager;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.List;

import cache.DataCache;

/**
 * @author CentMeng csdn@vip.163.com on 16/8/11.
 */
public class BaseActivity extends FragmentActivity {


    protected final static String TAG = "BaseActivity";

    /**
     * 快点时间
     */
    public final static int FAST_CLICK_TIME = 1000;

    protected Context context;

    public static ToastUtil toastUtil;

    public static ToastUtilHaveRight toastUtilRight;

    public BitmapCache bitmapCache;

    public static HttpService httpService;
    // 判断是不是第一次
    public static SharePreferenceStorageService preferenceStorageService;

    public VolleyHttpClient volleyHttpClient;

    public Dialog progressDialog;

    public GlobalPhone app;

    private LruCache<String, Bitmap> memoryCache;

    private DiskLruCache diskLruCache;

    public final static int avatarWidth = 150;

    public final static int pictureWidth = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBasic();
        initNet();
        initFresco();
        initDateBase();

        ActivityCollector.currentActivity = this;
        ActivityCollector.addActivity(this);
        Log.e("DEBUG", String.valueOf(ApiSettings.debug));
        Log.e("BUILDDEBUG", String.valueOf(BuildConfig.BUILD_TYPE));
    }

    private void initBasic() {
        this.context = BaseActivity.this;
        app = GlobalPhone.getInstance();
        if (app.getUploadManager() == null) {
            UploadManager uploadManager = UploadUtil.getUploadManager();
            app.setUploadManager(uploadManager);
        }
        preferenceStorageService = SharePreferenceStorageService
                .newInstance(getApplicationContext());
        if (toastUtil == null) {
            toastUtil = new ToastUtil(this);
        }
        initFontScale();
    }

    private void initFontScale() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1; //0.85 small size, 1 normal size, 1,15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    /**
     * onCreate时候Fresco初始化
     */
    private void initFresco() {
        ImagePipelineConfig config = ConfigConstants.getImagePipelineConfig(this);
        Fresco.initialize(app, config);
    }

    /**
     * onCreate数据库初始化
     */
    private void initDateBase() {
        DataCache.newInstance(this);
    }

    /**
     * onCreateVolley初始化
     */
    private void initNet() {

        httpService = HttpService.newInstance(getApplicationContext());
        volleyHttpClient = VolleyHttpClient.newInstance(httpService, this);
    }

    /**
     * 使用volleyImageLoader初始化BitmapCatch
     */
    protected void initBitmapCache() {
        try {
            File cacheDir = FileUtils.getDiskFileDir(this, DiskCacheParams.DIR, Environment.DIRECTORY_PICTURES);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            if (diskLruCache == null) {
                //缓存地址，程序版本号，同一个Key对应多少个文件，可以缓存多少字节数据
                diskLruCache = DiskLruCache.open(cacheDir, BaseTools.getVersionCode(this), 1, DiskCacheParams.DEFAULT_DISKCACHE_SIZE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (memoryCache == null) {
            // 获取应用程序最大可用内存
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            // 设置图片缓存大小为程序最大可用内存的1/8
            int cacheSize = maxMemory / 8;
            memoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount();
//                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        bitmapCache = new BitmapCache(diskLruCache, memoryCache);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
        MobclickAgent.onPause(this);
    }

    /**
     * 没有提示语
     */
    public void showLoadingDialog() {
        showLoadingDialog(false, "");

    }

    public void showLoadingDialog(boolean canCancel, String msg) {
        showLoadingDialog(canCancel, msg, false);
    }

    /**
     * 提示语为空则提示指点君，在路途中
     *
     * @param msg
     */
    public void showLoadingDialog(String msg) {
        if (msg.isEmpty()) {
            msg = getString(R.string.loading);
        }
        showLoadingDialog(false, msg);

    }

    public void showLoadingDialog(boolean canCancel, String msg, boolean alive) {
        if (!alive && progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (!(alive && progressDialog != null)) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setCancelable(canCancel);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
            TextView message = (TextView) progressDialog
                    .findViewById(R.id.id_tv_loadingmsg);
            if (msg.isEmpty()) {
                message.setVisibility(View.INVISIBLE);
            } else {
                message.setVisibility(View.VISIBLE);
                message.setText(msg);
            }
        }
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showSystemToast(String msg) {
        if (!ToastUtil.isFastShow(3000)) {
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    public void showSystemShortToast(String msg) {
        if (!ToastUtil.isFastShow(2000)) {
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(String msg) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!ToastUtil.isFastShow(3000)) {
                toastUtil.show(msg);
            }
        } catch (Exception e) {
        }
    }

    public void showToastRight(String msg) {
        try {
            if (toastUtilRight == null) {
                toastUtilRight = new ToastUtilHaveRight(this.getApplicationContext());
            }
            toastUtilRight.show(msg);
        } catch (Exception e) {
        }
    }

    public void showToast(int res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            toastUtil.show(res);
        } catch (Exception e) {
        }
    }

    public void showToastShort(int res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!OnClickUtil.isFastDoubleClick(3000)) {
                toastUtil.showShort(res);
            }
        } catch (Exception e) {
        }
    }

    public void showToastShort(String res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!OnClickUtil.isFastDoubleClick(2000)) {
                toastUtil.showShort(res);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        System.out.println(this.getClass().getName());
//        if(!this.getClass().getName().contains("SplashActivity"))
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    private Dialog chooseSingeDialog = null;

    public void showChooseDateDialog(final String[] date, final String title, int chooseIndex) {
        View view = getLayoutInflater().inflate(R.layout.wheelview_dialog, null);
        chooseSingeDialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        chooseSingeDialog.setContentView(view, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
        Window window = chooseSingeDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        chooseSingeDialog.onWindowAttributesChanged(wl);
        TextView tv = (TextView) view.findViewById(R.id.wheelview_dialog_tv);
        tv.setText(title);
        final WheelView byWhat = (WheelView) view.findViewById(R.id.empty);
        byWhat.setAdapter(new ArrayWheelAdapter<String>(date));
        byWhat.setVisibleItems(5);
        byWhat.setCurrentItem(chooseIndex);
        TextView submit = (TextView) view.findViewById(R.id.tv_wheelview_dialog_submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getChooseDialog(byWhat.getCurrentItem(),
                        date[byWhat.getCurrentItem()], title);
                chooseSingeDialog.dismiss();

            }
        });
        TextView cancel = (TextView) view.findViewById(R.id.tv_wheelview_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                chooseSingeDialog.dismiss();

            }
        });
        // 设置点击外围解散
        chooseSingeDialog.setCanceledOnTouchOutside(true);
        chooseSingeDialog.show();
    }

    public void getChooseDialog(int index, String message, String title) {

    }

    public void getDoubleChooseDialog(int firstIndex, int secondIndex, String firstMessage, String secondMessage, String title) {

    }

    private Dialog chooseDoubleDialog = null;

    protected void showChooseDateDialog(final String[] date,
                                        final String[][] secondDate, final String title) {
        chooseDoubleDialog = null;
        View view = getLayoutInflater().inflate(
                R.layout.doublewheelview_dialog, null);
        chooseDoubleDialog = new Dialog(this,
                R.style.transparentFrameWindowStyle);
        chooseDoubleDialog.setContentView(view, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
        Window window = chooseDoubleDialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        chooseDoubleDialog.onWindowAttributesChanged(wl);
        TextView tv = (TextView) view.findViewById(R.id.wheelview_dialog_tv);
        tv.setText(title);
        final WheelView wv_first = (WheelView) view.findViewById(R.id.wv_first);
        final WheelView wv_second = (WheelView) view
                .findViewById(R.id.wv_second);
        wv_first.setAdapter(new ArrayWheelAdapter<String>(date));
        wv_first.setVisibleItems(5);
        wv_first.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                wv_second.setAdapter(new ArrayWheelAdapter<String>(
                        secondDate[newValue]));
                // wv_second.setCurrentItem(secondDate[newValue].length / 2);
                wv_second.setCurrentItem(0);
            }
        });
        wv_second.setVisibleItems(5);
        wv_first.setCurrentItem(1);
        wv_first.setCurrentItem(0);
        TextView submit = (TextView) view.findViewById(R.id.tv_wheelview_dialog_submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getDoubleChooseDialog(wv_first.getCurrentItem(), wv_second.getCurrentItem(), date[wv_first.getCurrentItem()], secondDate[wv_first.getCurrentItem()][wv_second
                        .getCurrentItem()], title);
                chooseDoubleDialog.dismiss();

            }
        });
        TextView cancel = (TextView) view.findViewById(R.id.tv_wheelview_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                chooseDoubleDialog.dismiss();

            }
        });
        // 设置点击外围解散
        chooseDoubleDialog.setCanceledOnTouchOutside(true);
        chooseDoubleDialog.show();
    }


    /**
     * 自定义对话框
     */

    PrompfDialog customDialog;

    public void showCustomDialog(final BaseActivity context, String title, String submitName, String cancelName, PrompfDialog.UpdateOnclickListener listener) {

        customDialog = null;
        customDialog = new PrompfDialog(context,
                R.style.transparentFrameWindowStyle, submitName, cancelName,
                title, this.getString(R.string.app_name));
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(false);
        customDialog
                .setUpdateOnClickListener(listener);
        Window window = customDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        if (!isFinishing()) {
            customDialog.show();
        }


    }


    private Dialog call_dialog = null;
    Button btn_call;

    public void showCallDialog(final String phone_num) {

        if (call_dialog == null) {
            View view = getLayoutInflater().inflate(R.layout.dialog_call, null);
            call_dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
            call_dialog.setContentView(view, new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            Window window = call_dialog.getWindow();
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = getWindowManager().getDefaultDisplay().getHeight() - 50;
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // 设置显示位置
            call_dialog.onWindowAttributesChanged(wl);
            // 设置点击外围解散
            call_dialog.setCanceledOnTouchOutside(true);
            call_dialog.show();
            btn_call = (Button) view.findViewById(R.id.btn_call);
            btn_call.setText(String.format("拨打电话:%1$s",phone_num));
            Button btn_kefu = (Button) view.findViewById(R.id.btn_kefu);
            btn_kefu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!OnClickUtil.isFastDoubleClick(FAST_CLICK_TIME)){
                        WebViewActivity.startAc(context,"在线客服","https://static.meiqia.com/dist/standalone.html?eid=25910");
                        call_dialog.dismiss();
                    }
                }
            });
            // btn_call.setText(phone_num);
            Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
            btn_call.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (phone_num != null && !phone_num.equals("")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri
                                .parse("tel:" + phone_num));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        call_dialog.dismiss();
                    }
                }
            });
            btn_cancle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    call_dialog.dismiss();
                }
            });
        } else {
            btn_call.setText(String.format("拨打电话:%1$s",phone_num));
            call_dialog.show();
        }
    }


    /**
     * 程序是否在前台运行
     */

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public VolleyHttpClient getHttpClient() {
        return volleyHttpClient;
    }

    protected GlobalWlanErrorListener errorListener = new GlobalWlanErrorListener(this) {
        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
        }
    };

    public GlobalWlanErrorListener getErrorListener() {
        return errorListener;
    }

    public boolean success(ApiResponse response) {
        try {
            cancelLoadingDialog();
        } catch (Exception e) {

        }
        if (response.isError()) {

            loginDisable(response, false);
            return false;
        }

        return true;
    }


    public void loginDisable(ApiResponse response, boolean needFinish) {
        if (response.getCode() == 208) {
            Intent intent = new Intent();
            intent.setAction(RetryLoginReceiver.RETRYLOGIN_RECEIVER_ACTION);
            sendBroadcast(intent);
            if (needFinish) {
                finish();
            }
        } else {
            //根据自己项目自己设定
            if (response.getCode() != 405 && response.getCode() != 303) {
                showSystemShortToast(response.getMsg());
            }
        }
    }
}
