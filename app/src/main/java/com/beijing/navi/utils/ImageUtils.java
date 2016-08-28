package com.beijing.navi.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.media.FaceDetector;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.core.constant.LogConstant;
import com.android.core.utils.LogUtils;
import com.android.volley.toolbox.ImageLoader;
import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;
import com.core.api.ApiSettings;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author CentMeng csdn@vip.163.com on 15/11/26.
 */
public class ImageUtils {

    /**
     * SimpleDrawView
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawView(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
    }

    /**
     * SimpleDrawView黄金比，宽高比,配合人脸识别
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618(final SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        sdv.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = sdv.getDrawingCache();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        PointF pointF = setFace(bitmap, sdv.getWidth(), sdv.getHeight());
                        //按百分比算，防止偏差
                        final PointF pointF1 = new PointF(pointF.x / (float) sdv.getWidth(), pointF.y / (float) sdv.getHeight());
//                        Activity activity = (Activity)sdv.getContext();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                        sdv.getHierarchy()
                                .setActualImageFocusPoint(pointF1);
                        sdv.setDrawingCacheEnabled(false);
//
//                            }
//                        });
                    }
//                }).start();


//        }

                    @Override
                    public void onIntermediateImageSet(String id, Object imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                }

        ));
        sdv.setAspectRatio(1.618f);
//        BitmapDrawable drawable = (BitmapDrawable) sdv.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        PointF pointF = setFace(bitmap,100,100);
//        sdv.getHierarchy()
//                .setActualImageFocusPoint(pointF);
    }

    /**
     * SimpleDrawView黄金比，宽高比,配合人脸识别，异步处理人脸识别
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618Ascy(final SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        sdv.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = sdv.getDrawingCache();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PointF pointF = setFace(bitmap, sdv.getWidth(), sdv.getHeight());
                                //按百分比算，防止偏差
                                final PointF pointF1 = new PointF(pointF.x / (float) sdv.getWidth(), pointF.y / (float) sdv.getHeight());
                                Activity activity = (Activity) sdv.getContext();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        sdv.getHierarchy()
                                                .setActualImageFocusPoint(pointF1);
                                        sdv.setDrawingCacheEnabled(false);

                                    }
                                });
                            }
                        }).start();


                    }

                    @Override
                    public void onIntermediateImageSet(String id, Object imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                }

        ));
        sdv.setAspectRatio(1.618f);
    }

    /**
     * SimpleDrawView黄金比，宽高比
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618WithoutFace(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1.618f);
    }


    /**
     * SimpleDrawView14比，宽高比
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy14(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1.4f);
    }

    /**
     * SimpleDrawView，宽高比0727,首页弹出的广告图
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy0727(SimpleDraweeView sdv, String preurl, int width, int height, ControllerListener controllerListener) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, controllerListener));
        sdv.setAspectRatio(0.727f);
    }

    /**
     * SimpleDrawView，宽高比4437,首页专题图
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy4437(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(4.437f);
    }

    /**
     * SimpleDrawView，宽高比1:1
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1f);
    }


    /**
     * 根据宽高展现
     *
     * @param sdv
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewByWh(SimpleDraweeView sdv, String preUrl, int width, int height) {
        String url = getUrl(preUrl, width, height);
        sdv.setController(getController(sdv, url, null));
        float f = (float) width / (float) height;
        if (f <= 0.5) {
            f = 0.727f;
        }
        sdv.setAspectRatio(f);
        /**
         * 第一次时候不显示问题
         */
//        if(width != 0 && height != 0) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
//            sdv.setLayoutParams(params);
//        }
    }


    /**
     * 未知尺寸，获取完图片之后根据比例展示
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewUnknowSizeByQiniu(final SimpleDraweeView sdv, final String preurl, final int width, final int height) {
//        final QiniuImageInfoRequest request = new QiniuImageInfoRequest(preurl);
//
//        request.setListener(new Response.Listener<ImageInfo>() {
//            @Override
//            public void onResponse(ImageInfo response) {
//
//                int width = sdv.getWidth();
//                float bili = (float) response.getHeight() / (float) response.getWidth();
//                float height = width * bili;
//                setSimpleDrawViewByWh(sdv, preurl, width, (int) height);
//            }
//        });
//        request.setErrorlistener(new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String url = getUrl(preurl, width, height);
//                setSimpleDrawViewBy1618(sdv, url, 0, 0);
//            }
//        });
//        if (useCache) {
//            activity.volleyHttpClient.doNetTask(VolleyHttpClient.GET, request, DataCacheType.TEMP_CACHE);
//        } else {
//            activity.volleyHttpClient.doNetTask(VolleyHttpClient.GET, request, DataCacheType.NO_CACHE);
//        }
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener<ImageInfo>() {
            @Override
            public void onSubmit(String id, Object callerContext) {

            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                LogUtils.e(LogConstant.RESULT_OPERA, "width=" + imageInfo.getWidth() + "height=" + imageInfo.getHeight() + " bili=" + (float) imageInfo.getWidth() / (float) imageInfo.getHeight());
                float f = (float) imageInfo.getWidth() / (float) imageInfo.getHeight();
                if (f <= 0.5) {
                    f = 0.727f;
                }
                sdv.setAspectRatio(f);
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

            }

            @Override
            public void onIntermediateImageFailed(String id, Throwable throwable) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {

            }

            @Override
            public void onRelease(String id) {

            }
        }));
        sdv.setAspectRatio(1f);
    }


    private static String getUrl(String preUrl, int width, int height) {
        String url = preUrl;
        if (width != 0 && height != 0) {
            url = String.format(ApiSettings.QINIU_CUTPHOTO_BYSIZE, preUrl, width, height);
        } else if (width != 0 && height == 0) {
            url = String.format(ApiSettings.QINIU_CUTPHOTO_BYSIZE_W, preUrl, width);
        }
        return url;
    }

    private static DraweeController getController(SimpleDraweeView sdv, String url, ControllerListener controllerListener) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setControllerListener(controllerListener)
                .setOldController(sdv.getController())
                .build();
        return controller;
    }

    public static void setImageView(final BaseActivity activity, final ImageView imv, final String preurl, final int width, final int height, int resLoadingId) {
        String url = preurl;
        if (width != 0 && height != 0) {
            url = String.format(ApiSettings.QINIU_CUTPHOTO_BYSIZE, preurl, width, height);
        } else if (width != 0 && height == 0) {
            url = String.format(ApiSettings.QINIU_CUTPHOTO_BYSIZE_W, preurl, width);
        }
        ImageLoader.ImageContainer imageContainer = activity.volleyHttpClient.loadingImage(imv, url, resLoadingId, R.drawable.bg_notus, activity.bitmapCache, null);
    }

    public static Bitmap getImageByFile(String uri) {
        try {
            FileInputStream fis = new FileInputStream(uri);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从服务器取图片，并保存流
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 异步获取图片
     *
     * @param url
     * @return
     */
    public static void getAyncHttpBitmap(final String url, final LoadBitmapCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(url)) {
                    URL myFileUrl = null;
                    try {
                        myFileUrl = new URL(url);
                    } catch (MalformedURLException e) {
                        callBack.onError(e);
                    }
                    if (myFileUrl != null) {
                        try {
                            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                            conn.setConnectTimeout(0);
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            callBack.onSuccess(BitmapFactory.decodeStream(is));
                            is.close();
                        } catch (IOException e) {
                            callBack.onError(e);
                        }
                    }
                }
            }
        }).start();
    }

    public static PointF setFace(Bitmap bitmap, int width, int height) {
        FaceDetector fd;
        FaceDetector.Face[] faces = new FaceDetector.Face[1];
        PointF midpoint = new PointF(width / 2.0f, height / 2.0f);
        int count = 0;
        try {

            Bitmap faceBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
            bitmap.recycle();
            fd = new FaceDetector(faceBitmap.getWidth(), faceBitmap.getHeight(), 1);
            count = fd.findFaces(faceBitmap, faces);
            faceBitmap.recycle();
        } catch (Exception e) {
            LogUtils.e("检测脸部", "setFace(): " + e.toString());
            return midpoint;
        }

// check if we detect any faces
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                try {
                    faces[i].getMidPoint(midpoint);
                } catch (Exception e) {
                    LogUtils.e("检测脸部", "setFace(): face " + i + ": " + e.toString());
                }
            }

        }


        return midpoint;
    }
}
