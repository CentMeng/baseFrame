package com.core.api.event.request;

import com.android.volley.Response;
import com.core.api.common.HttpParam;
import com.core.api.event.ApiRequest;

/**
 * Created by suetming on 15-9-17.
 */
public class Request extends ApiRequest {

    public HttpParam httpParam;

    public Request(String values, Class<?> clazz) {
        super(values, clazz, false);
        this.httpParam = new HttpParam();
    }

    public Request success(Response.Listener<?> listener) {
        setListener(listener);
        return this;
    }

    public Request error(Response.ErrorListener listener) {
        setErrorlistener(listener);
        return this;
    }


}
