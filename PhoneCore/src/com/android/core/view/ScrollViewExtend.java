package com.android.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/***
 * @title 重写ScrollView 解决了在viewpager中添加ScrollView的问题
 * @author mengxc
 */
public class ScrollViewExtend extends ScrollView{

	private float xDistance;
	private float yDistance;
	private float xLast;
	private float yLast;


	public ScrollViewExtend(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrollViewExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewExtend(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0.0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);

			//左右滑动
			if(xDistance > yDistance)
				return false;

			break;
		default:
			break;
		}
		return true;
	}


}