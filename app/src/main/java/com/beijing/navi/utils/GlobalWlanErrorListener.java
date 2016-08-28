package com.beijing.navi.utils;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.beijing.navi.BaseActivity;
import com.beijing.navi.R;
import com.core.api.utils.NetWorkUtils;


public class GlobalWlanErrorListener implements Response.ErrorListener {

	private BaseActivity activity;

	private String wlanFalseMessage = "网络错误";

	public GlobalWlanErrorListener(Context context) {
		this.activity = (BaseActivity) context;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
        try {
            activity.cancelLoadingDialog();
        } catch (Exception e) {
            return;
        }
        try {
            if (!NetWorkUtils.detect(activity)) {
                // 无网情况提示
                activity.showSystemShortToast(activity
                        .getString(R.string.nonet));
            } else {
                // 返回数据为空提示
                if (error == null || error.networkResponse == null) {
                    activity.showSystemShortToast(activity
                            .getString(R.string.netlong));
                } else {
                    activity.showSystemShortToast(wlanFalseMessage);

                }
            }
        } catch (Exception e) {
            activity.showSystemShortToast("网络略有些问题,请稍后再试");
        }
    }

	public String getWlanFalseMessage() {
		return wlanFalseMessage;
	}

	public void setWlanFalseMessage(String wlanFalseMessage) {
		this.wlanFalseMessage = wlanFalseMessage;
	}

}
