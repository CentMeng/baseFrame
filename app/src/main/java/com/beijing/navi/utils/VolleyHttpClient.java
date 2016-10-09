package com.beijing.navi.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.core.utils.phone.LogUtils;
import com.android.core.utils.phone.NetWorkUtils;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.beijing.navi.BaseActivity;
import com.beijing.navi.GlobalPhone;
import com.beijing.navi.activity.LoginActivity_;
import com.core.api.ApiSettings;
import com.core.api.common.GsonRequestExt;
import com.core.api.common.HttpService;
import com.core.api.event.ApiRequest;
import com.core.api.utils.cache.BitmapCache;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import cache.DataCache;
import cache.DataCacheType;


public class VolleyHttpClient {

    public static final int GET = 1;

    public static final int GET_AOUTH = 2;

    public static final int POST = 3;

    public static final int POST_AOUTH = 4;

    public static final int GETSTRING = 5;

    public static final int GETSTRING_AOUTH = 6;

    public static final int POSTSTRING_AOUTH = 7;

    private static final String TAG = "VolleyHttpClient";

    private static HttpService httpService;
    private final static Gson gson = new Gson();

    private static BaseActivity activity;


//    public VolleyHttpClient version(String version) {
//        ApiSettings.API_VERSION = version;
//        return this;
//    }


    public VolleyHttpClient(HttpService httpService) {
        this.httpService = httpService;
    }

    /**
     * 传入初始化好的httpService 实例
     *
     * @param httpService
     * @return
     */
    public static VolleyHttpClient newInstance(HttpService httpService, Context context) {
        activity = (BaseActivity) context;
        if (httpService != null) {
            return new VolleyHttpClient(httpService);
        }
        return null;
    }

    public void post(ApiRequest apiRequest) {
        doNetTask(POST, apiRequest, DataCacheType.NO_CACHE);
    }

    public void postWithOAuth(ApiRequest apiRequest) {
        doNetTask(POST_AOUTH, apiRequest, DataCacheType.NO_CACHE);
    }

    public void get(ApiRequest apiRequest) {
        doNetTask(GET, apiRequest, DataCacheType.NO_CACHE);
    }

    public void getWithOAuth(ApiRequest apiRequest) {
        doNetTask(GET_AOUTH, apiRequest, DataCacheType.NO_CACHE);
    }

    public void doNetTask(int method, ApiRequest apiRequest) {
        doNetTask(method, apiRequest, DataCacheType.NO_CACHE);
    }

    public void doNetTask(int method, ApiRequest apiRequest, DataCacheType cacheType) {
        doNetTask(method, apiRequest, cacheType, false, null);
    }


    public void doNetTask(int method, ApiRequest apiRequest, DataCacheType cacheType,
                          boolean needLogin, BaseActivity activity) {
        doNetTask(method, apiRequest, cacheType, needLogin, activity, false);
    }


    public void doNetTask(int method, ApiRequest apiRequest, DataCacheType cacheType,
                          boolean needLogin, BaseActivity activity, boolean needFinish) {
        if (needLogin && activity.app.getUserParam() == null) {
            try {
                activity.cancelLoadingDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(activity, LoginActivity_.class);
            activity.startActivity(intent);
            if (needFinish) {
                activity.finish();
            }
            return;
        }
        switch (method) {
            case GET:
                //USER_OLD_CACHE有缓存先加载缓存数据，然后网络请求只是保存数据
                //TEMP_CACHE有缓存先加载缓存数据，不进行网络请求
                get(apiRequest, false, cacheType);
                break;
            case GET_AOUTH:
                get(apiRequest, true, cacheType);
                break;
            case POST:
                post(apiRequest, false, cacheType);
                break;
            case POST_AOUTH:
                post(apiRequest, true, cacheType);
                break;
            case GETSTRING:
                getByStringRequest(apiRequest, false, cacheType);
                break;
            case GETSTRING_AOUTH:
                getByStringRequest(apiRequest, true, cacheType);
                break;
            case POSTSTRING_AOUTH:
                postByStringRequest(apiRequest, true, cacheType);
                break;
        }
    }


    /**
     * 不用传header以及accesstoken的GET请求
     *
     * @param apiRequest
     */
    public void get(ApiRequest apiRequest, boolean needOauth, DataCacheType cacheType) {


        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        if (needOauth) {

            String accessToken = GlobalPhone.token;

            if(TextUtils.isEmpty(accessToken)){
                accessToken = DataCache.getDataCache()
                        .queryCache("access_token");
            }
            if (!TextUtils.isEmpty(accessToken)) {
                LogUtils.e("access_token", accessToken);
            }
//        header.put("Authorization", "Bearer" + " " + accessToken);
            header.put("token", accessToken);
        }
        String url = apiRequest.getUrl(Method.GET);
        Listener listener = apiRequest.getListener();
        LogUtils.e("------cacheType---------", cacheType.name());
        switch (cacheType) {
            case USE_OLD_CACHE:
            case TEMP_CACHE:
                String cacheStr ="";
                if(cacheType == DataCacheType.TEMP_CACHE){
                    cacheStr = DataCache.getDataCache().queryTempCache(url);
                }else {
                    cacheStr = DataCache.getDataCache().queryCache(url);
                }
                if (!TextUtils.isEmpty(cacheStr)) {
                    try {
                        if (apiRequest.getResponseType() != null) {
                            //有缓存先加载缓存数据
                            listener.onResponse(gson.fromJson(cacheStr, apiRequest.getResponseType()));
                            //有缓存先加载缓存数据，不进行网络请求
                            if (cacheType == DataCacheType.TEMP_CACHE) {
                                return;
                            }
                        } else {
                            listener.onResponse(cacheStr);
                            if (cacheType == DataCacheType.TEMP_CACHE) {
                                return;
                            }
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        GsonRequest request = new GsonRequest(Method.GET, url,
                apiRequest.getResponseType(), header, null,
                listener, apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        //网络错误并且使用TEMP_CACHE和use old cache还有待考察
        if (!useCache(apiRequest.getResponseType(), url, listener)) {
            httpService.addToRequestQueue(request);
        }

    }


    public void post(ApiRequest apiRequest, boolean needOauth, DataCacheType cacheType) {

        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        if (needOauth) {
            String accessToken = GlobalPhone.token;

            if(TextUtils.isEmpty(accessToken)){
                accessToken = DataCache.getDataCache()
                        .queryCache("access_token");
            }
            if (!TextUtils.isEmpty(accessToken)) {
                LogUtils.e("access_token", accessToken);
            }
//        header.put("Authorization", "Bearer" + " " + accessToken);
            header.put("token", accessToken);
        }

        LogUtils.e("------cacheType---------", cacheType.name());
        String url = apiRequest.getUrl(Request.Method.POST);
        Listener listener = apiRequest.getListener();
        if (apiRequest.getParams() != null && apiRequest.getParams().size() > 0) {
            //post有参数时候请求不建议使用use_old_cache方式，因为根据路径存储，post参数不在路径中
            if (cacheType == DataCacheType.USE_OLD_CACHE) {
                cacheType = DataCacheType.CACHE;
            }
        }
        switch (cacheType) {
            case USE_OLD_CACHE:
            case TEMP_CACHE:
                String cacheStr = DataCache.getDataCache().queryCache(url);
                if (!TextUtils.isEmpty(cacheStr)) {
                    try {
                        if (apiRequest.getResponseType() != null) {
                            //有缓存先加载缓存数据
                            listener.onResponse(gson.fromJson(cacheStr, apiRequest.getResponseType()));
                            //有缓存先加载缓存数据，不进行网络请求
                            if (cacheType == DataCacheType.TEMP_CACHE) {
                                return;
                            }
                        } else {
                            listener.onResponse(cacheStr);
                            if (cacheType == DataCacheType.TEMP_CACHE) {
                                return;
                            }
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        GsonRequest request = new GsonRequest(Request.Method.POST, url,
                apiRequest.getResponseType(), header,
                apiRequest.getParams(), listener,
                apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        //网络错误并且使用TEMP_CACHE和use old cache还有待考察
        if (!useCache(apiRequest.getResponseType(), url, listener)) {
            httpService.addToRequestQueue(request);
        }

    }

    /**
     * 图片请求
     */
    public ImageLoader.ImageContainer loadingImage(ImageView imageView, String url, int loadingResid,
                                                   int errorResid, BitmapCache mCache, ImageLoader.ImageListener listener) {
        if (listener == null) {
            listener = ImageLoader.getImageListener(imageView,
                    loadingResid, errorResid);
        }
        if (httpService.httpQueue != null) {
            ImageLoader imageLoader = new ImageLoader(httpService.httpQueue,
                    mCache);
            return imageLoader.get(url, listener);
        }

        return null;
    }

    public void loadingImage(ImageView imageView, String url, int loadingResid,
                             int errorResid, BitmapCache mCache) {
        loadingImage(imageView, url, loadingResid, errorResid, mCache, null);
    }

    /**
     * StringRequest
     */

    public void getByStringRequest(ApiRequest apiRequest, boolean needOauth, DataCacheType cacheType) {
        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        if (needOauth) {
            String accessToken = GlobalPhone.token;

            if(TextUtils.isEmpty(accessToken)){
                accessToken = DataCache.getDataCache()
                        .queryCache("access_token");
            }
            if (!TextUtils.isEmpty(accessToken)) {
                LogUtils.e("access_token", accessToken);
            }
//        header.put("Authorization", "Bearer" + " " + accessToken);
            header.put("token", accessToken);
        }
        String url = apiRequest.getUrl(Request.Method.GET);
        Listener listener = apiRequest.getListener();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                header, null, listener, apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        if (!useCache(null, url, listener)) {
            httpService.addToRequestQueue(request);
        }

    }

    /**
     * StringRequest
     */
    public void postByStringRequest(ApiRequest apiRequest,
                                    boolean needOauth, DataCacheType cacheType) {
        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        String url = apiRequest.getUrl(Request.Method.POST);
        Listener listener = apiRequest.getListener();

        if (needOauth) {
            String accessToken = GlobalPhone.token;

            if(TextUtils.isEmpty(accessToken)){
                accessToken = DataCache.getDataCache()
                        .queryCache("access_token");
            }
            if (!TextUtils.isEmpty(accessToken)) {
                LogUtils.e("access_token", accessToken);
            }
//        header.put("Authorization", "Bearer" + " " + accessToken);
            header.put("token", accessToken);
        }
        Map<String, String> params = new HashMap<String, String>();
        for (String key : apiRequest.getParams().keySet()) {
            params.put(key, (String) apiRequest.getParams().get(key));
        }
        StringRequest request = new StringRequest(Request.Method.POST, url,
                header, params, listener,
                apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        if (!useCache(null, url, listener)) {
            httpService.addToRequestQueue(request);
        }
    }

    /**
     * 断网情况使用缓存处理
     *
     * @param clazz
     * @param url
     * @param listener
     * @return
     */
    private boolean useCache(Class clazz, String url, Listener listener) {
        HttpService.httpQueue.getCache().invalidate(url, true);
        if (!NetWorkUtils.detect(httpService.getContext())) {
            String cacheStr = DataCache.getDataCache().queryCache(url);
            if (cacheStr != null) {
                try {
                    if (clazz != null) {
                        listener.onResponse(gson.fromJson(cacheStr, clazz));
                    } else {
                        listener.onResponse(cacheStr);
                    }
                    return true;

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public void getExtWithOAuth(com.core.api.event.request.Request apiRequest) {
        getExtWithOAuth(apiRequest, DataCacheType.NO_CACHE);
    }

    public void getExtWithOAuth(com.core.api.event.request.Request apiRequest, DataCacheType cacheType) {
        String url = apiRequest.getUrl(Request.Method.GET) + "?" + apiRequest.httpParam.encodeParametersToString("UTF-8");
        Listener listener = apiRequest.getListener();

        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);

        String accessToken = GlobalPhone.token;

        if(TextUtils.isEmpty(accessToken)){
            accessToken = DataCache.getDataCache()
                    .queryCache("access_token");
        }
        header.put("token", accessToken);

        GsonRequestExt request = new GsonRequestExt(Request.Method.GET, url,
                apiRequest.getResponseType(), header,
                apiRequest.httpParam, listener,
                apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        if (!useCache(apiRequest.getResponseType(), url, listener)) {
            httpService.addToRequestQueue(request);
        }
    }


    public void getExt(com.core.api.event.request.Request apiRequest, DataCacheType cacheType) {
        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        String url = apiRequest.getUrl(Request.Method.GET) + "?" + apiRequest.httpParam.encodeParametersToString("UTF-8");
        Listener listener = apiRequest.getListener();
        GsonRequestExt request = new GsonRequestExt(Request.Method.GET, url,
                apiRequest.getResponseType(), header,
                apiRequest.httpParam, listener,
                apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        if (!useCache(apiRequest.getResponseType(), url, listener)) {
            httpService.addToRequestQueue(request);
        }
    }


    public void postExt(com.core.api.event.request.Request apiRequest, DataCacheType cacheType) {
        Map<String, String> header = new HashMap<String, String>();
        if (apiRequest.getHeaders() != null) {
            header.putAll(apiRequest.getHeaders());
        }
        header.put("code", ApiSettings.VERSION_CODE);
        header.put("osType", ApiSettings.OSTYPE);
        String accessToken = GlobalPhone.token;

        if(TextUtils.isEmpty(accessToken)){
            accessToken = DataCache.getDataCache()
                    .queryCache("access_token");
        }

        if (!TextUtils.isEmpty(accessToken)) {
            header.put("token", accessToken);
        }

        String url = apiRequest.getUrl(Request.Method.POST);
        Listener listener = apiRequest.getListener();
        GsonRequestExt request = new GsonRequestExt(Request.Method.POST, url,
                apiRequest.getResponseType(), header,
                apiRequest.httpParam, listener,
                apiRequest.getErrorlistener(), cacheType);
        request.setShouldCache(false);
        if (!useCache(apiRequest.getResponseType(), url, listener)) {
            httpService.addToRequestQueue(request);
        }
    }

    // volley框架缓存
    // if (HttpService.httpQueue.getCache().get(url) != null) {
    // String cacheStr = new String(HttpService.httpQueue.getCache()
    // .get(url).data);
    //
    // if (cacheStr != null) {
    //
    // try {
    // listener.onResponse(cacheStr);
    //
    // } catch (JsonSyntaxException e) {
    // e.printStackTrace();
    // }
    //
    // return;
    // }
    //
    // }

}
