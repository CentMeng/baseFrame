/*
 * Copyright (c) 2014.
 * Jackrex
 */

package com.android.core.utils.Text;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.android.core.utils.Data.CalendarUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static final String[] UNCIVIL_TEXTS = "母,妹,狗血,垃圾,我去,SB,高能,山寨,中二,吹箫,艹,撸,装X,装13,坑爹,变态,装逼,中枪,v5,V5,MLGB,mlgb,肤浅,脑残,我X,了个,滚,无聊,没趣,没劲,白痴,小白,老公,老婆,尼玛,2B,2b,天朝,吊丝,贱,宾馆,广告,政府,人权,共产党,民主,台独,喷精,包夜,动乱,学生妹,护法,强奸,考试答案,qq,QQ".split(",");

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    // used by friendly_time
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    // used by toDate
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    public static boolean isStringEmpty(String string) {

        if (string == null) {
            return true;
        }
        if (TextUtils.isEmpty(string)) {
            return true;
        }

        return false;
    }

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static float GetDistance(float lng1, float lat1, float lng2,
                                    float lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return (float) s;
    }

    /**
     * 字符串敏感截取，超出�?..结束，处理后的长�?=num区分字母和汉�?
     *
     * @param str
     * @param num
     * @return String
     */
    public static String subStrSensitive(String str, int num) {
        if (str == null || num <= 3) {
            return str;
        }
        StringBuffer sbStr = new StringBuffer();
        String returnStr = "";
        int length = 0;
        char temp;
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if (isChinese(temp)) {
                length = length + 2;
            } else {
                length++;
            }
            if (length == num && i == (str.length() - 1)) {
                sbStr.append(temp);
                returnStr = sbStr.toString();
                break;
            } else if (length >= num) {
                sbStr.append("...");
                returnStr = sbStr.toString();
                try {
                    int lastL = returnStr.getBytes("GBK").length;

                    while (lastL > num) {
                        returnStr = returnStr.substring(0,
                                returnStr.length() - 4) + "...";

                        lastL = returnStr.getBytes("GBK").length;
                    }
                    break;
                } catch (Exception e) {
                    break;
                }
            }
            sbStr.append(temp);
            if (i == (str.length() - 1)) {

                returnStr = sbStr.toString();
            }
        }

        return returnStr;
    }

    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 19968 && v <= 171941);
    }

    public static boolean containsChinese(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }
        return false;
    }

    /**
     * 是中文或者英文
     *
     * @param s
     * @return
     */
    public static boolean isName(String s) {
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (!isChinese(s.charAt(i)) && !isEnglish(s.charAt(i)) && s.charAt(i) != ' ')
                return false;
        }
        return true;
    }

    public static boolean isEnglish(int word) {
        if (!(word >= 'A' && word <= 'Z')
                && !(word >= 'a' && word <= 'z')) {
            return false;
        }
        return true;
    }


    public static boolean containsNumber(String s) {
        if (null == s || "".equals(s.trim())) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) { //循环遍历字符串
            if (Character.isDigit(s.charAt(i))) { //用char包装类中的判断数字的方法判断每一个字符 isNumber=true;
                return true;
            }
        }
        return false;

    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static String defaultString(String value) {
        return defaultString(value, "");
    }

    public static String defaultString(String value, String defaultValue) {
        return (value == null) ? defaultValue : value;
    }

    public static String encodeHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        StringUtils.encodeHexString(bytes, buffer);
        return buffer.toString();
    }

    public static void encodeHexString(byte[] bytes, StringBuilder buffer) {
        for (byte b : bytes) {
            int hi = (b >>> 4) & 0x0f;
            int lo = (b >>> 0) & 0x0f;
            buffer.append(Character.forDigit(hi, 16));
            buffer.append(Character.forDigit(lo, 16));
        }
    }

    public static String join(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);

            if (i < (array.length - 1)) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static String createUrlWithParam(String url, String param) {
        if (url == null) {
            return null;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!url.endsWith("/")) {
            urlBuilder.append("/");
        }

        urlBuilder.append(param);
        return urlBuilder.toString();
    }

    public static String decodeMessage(String str, String jsonNodeName) {
        String result = str;
        try {
            JSONObject json = new JSONObject(str);
            result = (String) json.get(jsonNodeName);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String getMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.substring(8, 24).toString();
    }

    public static String convertStreamToString(InputStream is)
            throws IOException {
        if (is != null) {

            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Convert and replace "abc","2013-06-15 15:15:12" -> "abc","1371280512000"
     *
     * @param source
     * @return
     */
    public static String convertAndReplace(String source) {
        String formatString = "[\"]\\d{4}[\\-]\\d{2}[\\-]\\d{2} \\d{2}[\\:]\\d{2}[\\:]\\d{2}[\"]";

        Pattern pattern = Pattern.compile(formatString);
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            String find = matcher.group();
            int len = find.length();
            find = find.substring(1, len - 1);
            Calendar cal = CalendarUtils.YYYYMMDDHHMMSSToCalendar(find);
            // cal.add(Calendar.MONTH, 1);
            if (cal != null) {
                long time = cal.getTimeInMillis();
                String targetString = "\"" + String.valueOf(time) + "\"";
                source = source.replaceFirst(formatString, targetString);
            }
        }

        return source;
    }

    public static final String fullTohalf(String fullStr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < fullStr.length(); i++) {
            try {
                Tstr = fullStr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr;
    }

//    public static String urlWithParameters(String url,
//                                           HashMap<String, Object> params) {
//        StringBuilder builder = new StringBuilder();
//
//        if (params != null) {
//            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
//                if (builder.length() == 0) {
//                    if (url.contains("?")) {
//                        builder.append("&");
//                    } else {
//                        builder.append("?");
//                    }
//                } else {
//                    builder.append("&");
//                }
//                builder.append(entry.getKey()).append("=")
//                        .append(entry.getValue());
//            }
//        }
//
//        return url + builder.toString();
//    }

    public static String urlWithParameters(String url,
                                           Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();

        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
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

    public static void mergeParam(HashMap<String, Object> params,
                                  List<NameValuePair> valuePair) {
        if (params != null) {
            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                valuePair.add(new BasicNameValuePair(key, value));
            }
        }
    }

    public static String getCountString(float count) {
        int a = (int) count;
        float b = count - a;

        if (b > 0) {
            return String.format("%.1f", count);
        } else {
            return String.valueOf(a);
        }
    }

    // public static void mergeParam(HashMap<String, Object> params,
    // LinkedMultiValueMap valuePair) {
    // if (params != null) {
    // for (HashMap.Entry<String, Object> entry : params.entrySet()) {
    // String key = entry.getKey();
    // String value = String.valueOf(entry.getValue());
    // valuePair.add(key, value);
    // }
    // }
    // }

    public static String[] getIpAndPort(String ip) {
        if (ip == null || ip.trim().length() < 4) {
            return null;
        }
        String[] strs = null;

        int index = ip.indexOf(":");
        if (index != -1) {
            strs = ip.split(":");
        } else {
            strs = new String[]{ip, "80"};
        }
        return strs;
    }

    @SuppressLint("NewApi")
    public String insertWithOnConflict(String table, ContentValues initialValues) {
        StringBuilder col = new StringBuilder();
        col.append("INSERT");
        col.append(" INTO ");
        col.append(table);
        col.append('(');

        StringBuilder val = new StringBuilder();
        val.append(" VALUES (");

        try {
            int size = (initialValues != null && initialValues.size() > 0) ? initialValues
                    .size() : 0;
            if (size > 0) {
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    col.append((i > 0) ? "," : "");
                    col.append("[" + colName + "]");

                    Object o = initialValues.get(colName);
                    val.append((i > 0) ? "," : "");
                    val.append("\"");
                    val.append(o);
                    val.append("\"");
                    i++;
                }
                col.append(')');
                val.append(')');

                col.append(val);
                col.append(";");
                return col.toString();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 判定全球手机号
     *
     * @param mobiles
     * @param code    国家码
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles, String code) {
        boolean flag = false;
        try {
            flag = isNumber(mobiles);
            if (code.equals("86")) {
                flag = isChineseMobileNO(mobiles);
            } else {
                if (mobiles.trim().length() > 12 || mobiles.trim().length() < 6) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判定中国手机号
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isChineseMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7]|(17[0,6,7,8])))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static boolean isNumber(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String getASCII(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    public static String getStringToNumber(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            {
                sbu.append(msg.get("" + chars[i]));
            }
        }
        return sbu.toString();
    }

    private static Map<String, String> msg = new HashMap<String, String>();

    static {
        msg.put("0", "0");
        msg.put("1", "1");
        msg.put("2", "2");
        msg.put("3", "3");
        msg.put("4", "4");
        msg.put("5", "5");
        msg.put("6", "6");
        msg.put("7", "7");
        msg.put("8", "8");
        msg.put("9", "9");
        msg.put("a", "10");
        msg.put("b", "11");
        msg.put("c", "12");
        msg.put("d", "13");
        msg.put("e", "14");
        msg.put("f", "15");
        msg.put("g", "16");
        msg.put("h", "17");
        msg.put("i", "18");
        msg.put("j", "19");
        msg.put("k", "20");
        msg.put("l", "21");
        msg.put("m", "22");
        msg.put("n", "23");
        msg.put("o", "24");
        msg.put("p", "25");
        msg.put("q", "26");
        msg.put("r", "27");
        msg.put("s", "28");
        msg.put("t", "29");
        msg.put("u", "30");
        msg.put("v", "31");
        msg.put("w", "32");
        msg.put("x", "33");
        msg.put("y", "34");
        msg.put("z", "35");
        msg.put("A", "10");
        msg.put("B", "11");
        msg.put("C", "12");
        msg.put("D", "13");
        msg.put("E", "14");
        msg.put("F", "15");
        msg.put("G", "16");
        msg.put("H", "17");
        msg.put("I", "18");
        msg.put("J", "19");
        msg.put("K", "20");
        msg.put("L", "21");
        msg.put("M", "22");
        msg.put("N", "23");
        msg.put("O", "24");
        msg.put("P", "25");
        msg.put("Q", "26");
        msg.put("R", "27");
        msg.put("S", "28");
        msg.put("T", "29");
        msg.put("U", "30");
        msg.put("V", "31");
        msg.put("W", "32");
        msg.put("X", "33");
        msg.put("Y", "34");
        msg.put("Z", "35");
        msg.put(".", "36");
        msg.put("@", "37");
        msg.put("_", "38");
    }

    public static String getUrlEncodeString(String content) {
        String content_ = content;
        try {
            content_ = URLEncoder.encode(content, "utf-8").replace("+","%20");
        } catch (Exception e) {
            content_ = content;
        }
        return content_;
    }

    public static String getUrlDecodeString(String content) {
        String content_ = content;
        try {
            content_ = URLDecoder.decode(content, "utf-8");
        } catch (Exception e) {
            content_ = content;
        }
        return content_;
    }

    public static String inputStreamToString(InputStream in_temp) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(in_temp));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static InputStream stringToInputStream(String str, String code) {
        if (TextUtils.isEmpty(code)) {
            return new ByteArrayInputStream(str.getBytes());
        } else {
            try {
                return new ByteArrayInputStream(str.getBytes(code));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new ByteArrayInputStream(str.getBytes());
            }
        }
    }


    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public static int dealStr(String s){
        int index = s.lastIndexOf(".");
        if(index > -1) {
            int len = s.substring(index + 1).length();
            return len;
        }
        return 0;
    }

    /**
     * 去除html标记
     */
    public static String removeHtml(String htmlStr){
        if(TextUtils.isEmpty(htmlStr)){
            return "";
        }
        String html = htmlStr;
        html = html.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
        html = html.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
        html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;","&");
        html = html.replaceAll("&lt;", "<");
        html = html.replaceAll("&gt;", ">");
        html = html.replaceAll("&nbsp;", " ");
        return html;
    }

    /**
     *
     * @param htmlStr
     * @param oldStr <img(.*?)
     * @param newStr <img
     * @return
     */
    public static String editHtml(String htmlStr,String oldStr,String newStr){
//        if(TextUtils.isEmpty(oldStr)){
//            return "";
//        }
        String html  = htmlStr;
        html = html.replaceAll("<img","<center\\><img");//Removes all items in brackets
        html = html.replaceFirst("\\>", "\\></center\\>");
        return html;
    }

    public static String getChineseNumber(int number){
        switch (number){
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            default:
                return "";
        }
    }

    public static String formatChineseNumber(int number){
        if(number< 10){
            return getChineseNumber(number);
        }else {
            int shi = (int) Math.floor(number/10);
            if(shi >1){
            return getChineseNumber(shi)+"十"+getChineseNumber(number);
            }else {
                return "十"+getChineseNumber(number);
            }
        }
    }

    public static String filterUncivilText(String text){
        for(String str: UNCIVIL_TEXTS){
            text = text.replace(str,"***");
        }
        return text;
    }
    public static boolean isHasUncivilText(String text){
        for(String str: UNCIVIL_TEXTS){
            if(text.contains(str)){
                return true;
            }
        }
        return false;
    }


    public static boolean isGeaterTo(String text,int size){

        try {
            byte[] bytes = text.getBytes("utf-8");
            if (bytes.length > size*3) {
                return true;
            }else {
                return false;
            }
            }catch (Exception e){
            return false;
        }
    }

    public static boolean isStartWithHttp(String str){
        return str.startsWith("http://") || str.startsWith("https://");
    }

    public static String addHttp(String str){
        return String.format("http://%1$s",str);
    }


    /**
     *  获取内容解码后的html
     * @param htmlStr
     * @return
     */
    public static String getDecodeBodyHtml(String htmlStr){
        if(isContainChinese(htmlStr)){
            return htmlStr;
        }

        ArrayList<String> strs = new ArrayList<>();

        byte[] bytes = htmlStr.getBytes();

        int lastindex = 0;
        for(int index = 1 ; index < bytes.length ; index ++){
            byte by = bytes[index];
            if(by == '>'){
                if(index < bytes.length -1 && bytes[index+1] != '<'){
                    strs.add(htmlStr.substring(lastindex, index+1));
                    lastindex = index+1;
                }else if(index == bytes.length -1){
                    strs.add(htmlStr.substring(lastindex, bytes.length));
                }
            }else if( by == '<'){
                if(index-1 < bytes.length && index-1>0 && bytes[index-1]!='>'){
                    strs.add(getUrlDecodeString(htmlStr.substring(lastindex, index)));
                    lastindex = index;
                }
            }
        }

        StringBuffer buffer = new StringBuffer();
        for(String str : strs){
            buffer.append(str);
        }

        return buffer.toString();
    }
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    //	public static boolean isLetter(String word){
//		boolean flag = false;
//		Pattern p = Pattern.compile("^[a-zA-Z]*");
//		Matcher m = p.matcher(word);
//		flag = m.matches();
//		return flag;
//	}

}
