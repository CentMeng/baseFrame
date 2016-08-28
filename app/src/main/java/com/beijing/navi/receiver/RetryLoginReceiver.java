package com.beijing.navi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.beijing.navi.activity.LoginActivity_;
import com.beijing.navi.utils.ActivityCollector;


public class RetryLoginReceiver extends BroadcastReceiver {

	public static String RETRYLOGIN_RECEIVER_ACTION = "android.msj.intent.action.RETRYLOGIN_RECEIVER_ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(RETRYLOGIN_RECEIVER_ACTION)) {
			if (!ActivityCollector.isExistActivity(LoginActivity_.class)) {
				Intent login_intent = new Intent(context, LoginActivity_.class);
				login_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(login_intent);
//				context.app.setUserParam(null);
			}

		}
	}

}
