package com.android.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 模仿大众点评 价格一直显示在标题
 * 
 * @author mengxc
 */
public class DiscountScrollView extends ScrollView {
	private OnScrollListener onScrollListener;
	private float xDistance;
	private float yDistance;
	private float xLast;
	private float yLast;

	public DiscountScrollView(Context context) {
		this(context, null);
	}

	public DiscountScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DiscountScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollListener != null) {
			onScrollListener.onScroll(t);
		}
	}

	 /** 
	 * 滚动的回调接口
	 * 
	 * @author mengxc
	 * 
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * 
		 * @param scrollY
		 *            、
		 */
		public void onScroll(int scrollY);
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

			if (xDistance > yDistance)
				return false;

			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
