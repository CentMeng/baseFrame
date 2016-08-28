package com.core.api.common;

/**
 * Created by suetming on 15-9-17.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author suetming (suetming.ma@luoteng.net)
 *
 */
public class HttpParam extends HashMap<String, List<String>> {

    private static final long serialVersionUID = 1L;

    public HttpParam() {
        super();
    }

    public HttpParam(int capacity) {
        super(capacity);
    }

    public HttpParam(Map<String, List<String>> map) {
        super(map);
    }

    public HttpParam(int capacity, float loadFactor) {
        super(capacity, loadFactor);
    }

    /*
     * This is the method to use for adding post parameters
     */
    public void add(String key, String value) {
        if (containsKey(key)) {
            get(key).add(value);
        }
        else {
            ArrayList<String> list = new ArrayList<String>();
            list.add(value);
            put(key, list);
        }
    }

    /**
     * Converts the Map into an application/x-www-form-urlencoded encoded string.
     */
    public byte[] encodeParameters(String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, List<String>> entry : entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), paramsEncoding);
                for (String value : entry.getValue()) {
                    encodedParams.append(key);
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(value, paramsEncoding));
                    encodedParams.append('&');
                }
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding,     uee);
        }
    }

    public String encodeParametersToString(String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, List<String>> entry : entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), paramsEncoding);
                for (String value : entry.getValue()) {
                    encodedParams.append(key);
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(value, paramsEncoding));
                    encodedParams.append('&');
                }
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding,     uee);
        }
    }

}

