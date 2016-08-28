package com.core.api.event.request;

import com.core.api.common.ApiConstants;
import com.core.api.event.response.Response;

/**
 * Created by suetming on 15-9-23.
 */
public class UploadTokenRequest extends Request implements ApiConstants {

    public UploadTokenRequest() {
        super(API_UPLOAD_TOKEN, Response.class);
    }

}
