package com.android.core.view;

import android.content.Context;
import android.util.AttributeSet;
/***
 * @title  圆形ImageView
 * 
 */
public class RoundImageView2 extends RoundImageView {

	public RoundImageView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		setRectAdius(180);
	}

	public RoundImageView2(Context context) {
		super(context);
		setRectAdius(180);
	}
}