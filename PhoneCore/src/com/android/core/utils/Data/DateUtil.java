/*
 * Copyright (c) 2014.
 * Jackrex
 */

package com.android.core.utils.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Jackrex on 2/18/14.
 */
public class DateUtil {

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    
    

    /**
     * 返回 format 格式
     *
     * @param date
     * @return
     */
    public static String format(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);//添加的时间操作的时间
        } catch (Exception e) {
            return null;
        }

    }
    /**
     * 返回 format 格式
     *
     * @param date
     * @return
     */
    public static String format(long date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);//添加的时间操作的时间
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 返回 yyyy年MM月dd日
     *
     * @return
     */
    public static String format(long time) {
        try {
        	
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(new Date(time));//添加的时间操作的时间
        } catch (Exception e) {
            return null;
        }

    }

    
    
    /**
     * 返回 yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(date);//添加的时间操作的时间
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 返回 yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static String format(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(date);//添加的时间操作的时间
        } catch (Exception e) {
            return null;
        }

    }

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
        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
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
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
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
    /**
     * 以友好的方式显示时间
     *
     * @param date
     * @return
     */
    public static String friendly_time(long date) {
        Date time = new Date(date);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
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
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
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

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }
    
    public static boolean isToday(long date){
    	boolean b = false;
    	Date time = new Date(date);
    	Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }
    
    /**
     * 判断两个给定的时间是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(long date1,long date2){
    	boolean b = false;
    	Date day1 = new Date(date1);
    	Date day2 = new Date(date2);
    	String sdate1 = dateFormater2.get().format(day1);
    	String sdate2 = dateFormater2.get().format(day2);
    	if(sdate1.equals(sdate2)){
    		b = true;
    	}
    	return b;
    }


    // 比较两个date类型的数据的大小
    public static int compare_Date(Date date1, Date date2) {

        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }

    }

    /**
     * 返回两个时间相差时间 样式*天*小时*分钟
     * @param time1
     * @param time2
     * @return
     */
    public static String dissTime(long time1,long time2){
        long diff = Math.abs(time1-time2);
        long ONE_MINUTE = 1000*60;
        long ONE_HOUR = ONE_MINUTE*60;
        long ONE_DAY = ONE_HOUR*24;

        int day =  new BigDecimal(diff).divide(new BigDecimal(ONE_DAY),0,BigDecimal.ROUND_DOWN).intValue();
        int hour = new BigDecimal (diff - day*(ONE_DAY)).divide(new BigDecimal(ONE_HOUR),0,BigDecimal.ROUND_DOWN).intValue();
        int minute = new BigDecimal((diff - day*ONE_DAY-hour*ONE_HOUR)).divide(new BigDecimal(ONE_MINUTE), 0, BigDecimal.ROUND_DOWN).intValue();
        String time = "";
        if(day >0){
            time  += ""+day+"天";
        }
        if(hour >0){
            time += ""+hour+"小时";
        }
        if(minute >0){
            time += ""+minute+"分";
        }
        return time;
    }

    /**
     * 根据时区返回时间
     * @param timeZoneId 时区id
     * @return
     */
    public static Date getTimeByTimeZone(String timeZoneId){
        return Calendar.getInstance(TimeZone.getTimeZone(timeZoneId)).getTime();
    }

    public static boolean isDaylightTime(int offset,String ID){
        SimpleTimeZone timeZone = new SimpleTimeZone(offset,
                ID,
                Calendar.APRIL, 1, -Calendar.SUNDAY,
                7200000,
                Calendar.OCTOBER, -1, Calendar.SUNDAY,
                7200000,
                3600000);
        return timeZone.useDaylightTime();
    }


}
