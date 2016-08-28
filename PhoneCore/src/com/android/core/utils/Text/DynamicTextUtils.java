package com.android.core.utils.Text;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

/**
 * Created by qiuzhenhuan on 16/2/24.
 */
public class DynamicTextUtils implements Runnable{


    private TextView mTvMoney;

    private double mMoneySize;

    private double mDifferSize;

    private int s = 50;

    private double mStartNum;



    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void run() {


        double i = 0;
        mStartNum = getStartSize();

        if(mStartNum>mMoneySize){

            if (mDifferSize > 99999) {
                i = mStartNum - 12131.12;
            } else if (mDifferSize > 9999) {
                i = mStartNum - 1213.21;
            } else if (mDifferSize > 999) {
                i = mStartNum - 102.12;
            } else if (mDifferSize > 99) {
                i = mStartNum - 11.21;
            } else {
                i = mStartNum - 1.11;
            }

            if (i > (mMoneySize + mDifferSize /10)) {
                double d = Double.parseDouble(String.format("%.2f", i));

                setFormaterText(mTvMoney,d);

                if (i > mMoneySize) {
                    mHandler.postDelayed(this, s);
                }

            } else {

                setFormaterText(mTvMoney,mMoneySize);

            }


        }else {
            if (mDifferSize > 99999) {
                i = mStartNum + 12131.12;
            } else if (mDifferSize > 9999) {
                i = mStartNum + 1213.21;
            } else if (mDifferSize > 999) {
                i = mStartNum + 102.12;
            } else if (mDifferSize > 99) {
                i = mStartNum + 11.21;
            } else {
                i = mStartNum + 1.11;
            }


            if (i < (mMoneySize - mDifferSize / 10)) {

                setFormaterText(mTvMoney,i);

                if (i < mMoneySize) {
                    mHandler.postDelayed(this, s);
                }

            } else {
                double d = Double.parseDouble(String.format("%.2f", mMoneySize));
                setFormaterText(mTvMoney,d);

            }

        }
    }

    public DynamicTextUtils(TextView mTvMoney, double mMoneySize){
        this.mTvMoney = mTvMoney;
        this.mMoneySize = mMoneySize;

        mStartNum =  getStartSize();

        mDifferSize = Math.abs(mMoneySize - mStartNum);
    }


    private void setFormaterText(TextView txt,double size){

        String strI = String.format("%.2f",size);
        if (strI.length() <= 6) {
            txt.setText(strI);
        } else if (strI.length() > 6 && strI.length() <= 9) {
            String s = strI.substring(0, strI.length() - 6) + ","
                    + strI.substring(strI.length() - 6);
            txt.setText(s);
        } else {
            String s = strI.substring(0, strI.length() - 9)
                    + ","
                    + strI.substring(strI.length() - 9,
                    strI.length() - 6) + ","
                    + strI.substring(strI.length() - 6);
            txt.setText(s);
        }
    }

    public double getStartSize() {
        String str;
        if (mTvMoney.getText().toString().indexOf(",") >= 0) {
            str = mTvMoney.getText().toString().replace(",", "");
        } else {
            str = mTvMoney.getText().toString();
        }
        return Double.parseDouble(str);
    }
}
