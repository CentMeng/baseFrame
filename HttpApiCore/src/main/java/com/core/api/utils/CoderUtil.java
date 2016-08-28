package com.core.api.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by qiuzhenhuan on 16/4/6.
 */
public class CoderUtil {

    public static String getDecoderStr(String str){
        String utfStr;
        try {
            utfStr = URLDecoder.decode(str,"utf-8");
        } catch (Exception e) {
            utfStr = str;
        }
        return utfStr;
    }
    public static String getEncoderStr(String str){
        String utfStr;
        try {
            utfStr = URLEncoder.encode(str, "utf-8").replace("+","%20");
        } catch (Exception e) {
            utfStr = str;
        }
        return utfStr;
    }
}
