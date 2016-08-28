package com.core.api.event;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.core.api.ApiSettings;

import java.util.HashMap;
import java.util.Map;


public class ApiRequest {

    public String url = "";

    protected Map<String, Object> params = null;

    private Class<?> clazz;

    private Response.Listener<?> listener;

    private Response.ErrorListener errorlistener;

    protected Map<String, String> headers;

    public ApiRequest(String values, Class<?> clazz) {
        this(values, clazz, false);
    }

    public ApiRequest(String values, Class<?> clazz, boolean isChonggou) {
        if (isChonggou) {
            this.url = values;
        } else {
            this.url = String.format("%1$s%2$s", ApiSettings.URL_BASE, values);
        }
        this.clazz = clazz;
        this.params = new HashMap<String, Object>();
//        params.put("versionCode",ApiSettings.VERSION_CODE);
        this.headers = new HashMap<String, String>();
    }


    public String getUrl(int method) {
        switch (method) {
            case Method.GET:
                return urlWithParameters(url, params);
            case Method.POST:
                return url;
        }
        return url;
    }

    public Map<String, Object> getParams() {
        if (params.isEmpty()) {
            Log.e("REQUEST_PARAMS", "null");
            return null;
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Class getResponseType() {
        return clazz;
    }

    public Response.Listener<?> getListener() {
        return listener;
    }

    public void setListener(Response.Listener<?> listener) {
        this.listener = listener;
    }

    public Response.ErrorListener getErrorlistener() {
        return errorlistener;
    }

    public void setErrorlistener(Response.ErrorListener errorlistener) {
        this.errorlistener = errorlistener;
    }

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            Log.e("REQUEST_Header", "null");
            return null;
        }
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addParams(Map<String, Object> params) {
        if (this.params != null) {
            this.params.putAll(params);
        } else {
            this.params = new HashMap<String, Object>();
            this.params.putAll(params);
        }
    }


    public static String urlWithParameters(String url,
                                           Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();

        if (params != null) {
            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                if (builder.length() == 0) {
                    if (url.contains("?")) {
                        builder.append("&");
                    } else {
                        builder.append("?");
                    }
                } else {
                    builder.append("&");
                }
                builder.append(entry.getKey()).append("=")
                        .append(entry.getValue());
            }
        }

        return url + builder.toString();
    }


}
