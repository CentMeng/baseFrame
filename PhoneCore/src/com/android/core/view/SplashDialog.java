package com.android.core.view;


import com.android.core.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashDialog extends Dialog {
	private Context context;
	private Window window;
	private String content;
	private TextView tv_con;
	Animation operatingAnim;
	ImageView iv;

	public SplashDialog(Context context, int theme, String content) {
		super(context, theme);
		this.context = context;
		this.content = content;
		window = getWindow();
		window.setWindowAnimations(R.style.animdialogstyle);
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.splash);
		initViews();
	}

	private void initViews() {
		tv_con = (TextView) findViewById(R.id.tv_con);
		tv_con.setText(this.content);
		iv = (ImageView) findViewById(R.id.loading_process_dialog_progressBar);
		operatingAnim = AnimationUtils.loadAnimation(context, R.anim.xuanz);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		if (iv != null)
			iv.clearAnimation();
	}

	@Override
	public void show() {
		super.show();
		if (iv != null)
			iv.startAnimation(operatingAnim);
	}

}
