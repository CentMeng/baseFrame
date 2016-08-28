package com.core.api.event.request;

import com.core.api.common.ApiConstants;
import com.core.api.event.ApiRequest;
import com.core.api.event.response.ContextResponse;

/**
 * @author CentMeng csdn@vip.163.com on 15/9/15.
 */
public class ContextRequest extends ApiRequest{

    public ContextRequest() {
        super(ApiConstants.API_CONTEXT, ContextResponse.class);
    }
}
