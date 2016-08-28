package com.core.api.common;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.GsonRequest;

import java.util.Map;

import cache.DataCacheType;

/**
 * @author suetming (suetming.ma@luoteng.net)
 *
 */
public class GsonRequestExt<T> extends GsonRequest<T> {


    private HttpParam params;


    public GsonRequestExt(int method, String url, Class<T> clazz,
                          Map<String, String> headers, HttpParam params,
                          Listener<T> listener, ErrorListener errorListener, DataCacheType cacheType) {
        super(method, url, clazz, headers, null, listener, errorListener, cacheType);
        this.params = params;
    }


    /* (non-Javadoc)
     * @see com.android.volley.Request#getBody()
     */
    @Override
    public byte[] getBody() throws AuthFailureError {
        if (params != null && params.size() > 0) {
            return params.encodeParameters(getParamsEncoding());
        }
        return null;
    }
}

