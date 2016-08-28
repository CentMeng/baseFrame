package com.android.core.utils.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author CentMeng csdn@vip.163.com on 15/12/1.
 */
public class NumberUtils {

    /**
     * 返回100，000.00元有千位符和后两位小数
     *
     * @param amount
     * @return
     */
    public static String getSeperateWithFloatAmount(double amount) {
        // String format = "%1$s 元";
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        String point_total = "." + total.substring(total.length() - 2);
        // return
        return addSeparateSign((int) amount) + point_total;
    }

    /**
     * 返回100，000元有千位符整数
     *
     * @param amount
     * @return
     */
    public static String getSeperateAmount(double amount) {
        String double_format = "%1$.2f";
        String total = String.format(double_format, amount);
        // return String.format(format,
        // "" +
        return addSeparateSign((int) Float.parseFloat(total));
    }

    /**
     * 返回***万
     *
     * @param amount
     * @return
     */
    public static String getWanAmount(double amount) {
        int total = (int) (amount / 10000);
        return "" + total + "万";
    }


    /**
     * 将整型添加“,”隔位符
     * “123456789”-》“123，456，789”
     *
     * @param number
     * @return
     */
    public static String addSeparateSign(int number) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(number);
    }


    public static String doubleTrans(int num){
//        BigDecimal account = new BigDecimal(String.valueOf(num));
//        DecimalFormat df=new java.text.DecimalFormat("0.##");
//        return df.format(account.movePointLeft(2).floatValue());

        return getSeperateWithFloatAmount(num/100.00
        );
    }

}
