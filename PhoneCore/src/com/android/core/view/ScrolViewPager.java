package com.android.core.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @title 重写的ViewPager 解决在其中添加viewpager滑动冲突问题
 */
public class ScrolViewPager extends ViewPager {

	public ScrolViewPager(Context context) {
		super(context);
	}

	public ScrolViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
//   滑动冲突问题
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		boolean ret = super.dispatchTouchEvent(ev);
		if (ret) {
			requestDisallowInterceptTouchEvent(true);
		}
		return ret;
	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//		int height = 0;
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height)
//				height = h;
//		}
//
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}

}