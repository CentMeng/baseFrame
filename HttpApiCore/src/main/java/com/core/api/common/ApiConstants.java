package com.core.api.common;

import com.core.api.ApiSettings;

/**
 * Created by CentMeng csdn@vip.163.com on 15/9/8.
 * 请求地址
 */
public interface ApiConstants {

    boolean debug = ApiSettings.debug;

    /**
     * 登陆
     */
    String API_LOGIN = "/v1/user/login";


    /**
     * 注册
     */
    String API_REGISTER = "/v1/user/register";


    /**
     * 获取全局常量
     */
    String API_CONTEXT = "/v2/context";


    String API_UPLOAD_TOKEN = "/v1/upload/token";

}
