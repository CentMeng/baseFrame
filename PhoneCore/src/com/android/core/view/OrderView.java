package com.android.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

public class OrderView extends LinearLayout {

	private Scroller scroller;
	private Context mContext;

	private View alphaView;

	private View stayView;
	private StayViewListener stayViewListener;
	private ScrollView scrollView;

/**
 * 
 * @param stayview
 * @param scrollview
 * @param stayViewListener
 */
	public void setStayView(View stayview, ScrollView scrollview,
			StayViewListener stayViewListener) {
		this.stayView = stayview;
		this.scrollView = scrollview;
		this.stayViewListener = stayViewListener;

	}

	public void setStayView(View alphaView,View stayview, ScrollView scrollview,
							StayViewListener stayViewListener) {
		this.stayView = stayview;
		this.scrollView = scrollview;
		this.stayViewListener = stayViewListener;
		this.alphaView = alphaView;

	}

	public OrderView(Context context) {
		super(context);
		mContext = context;

	}

	public OrderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();

	}

	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		scroller = new Scroller(mContext);
	}


	boolean up = true;

	@Override
	public void computeScroll() {
		if (stayView != null && scrollView != null && stayViewListener != null) {
			int y = scrollView.getScrollY();
			if (up) {
				int top = stayView.getTop()-stayView.getHeight();
				System.out.println("visible----y------top----marginTop------"+y+" "+top+" "+stayView.getHeight());
				if (y >= top) {
					stayViewListener.onStayViewShow();
					up = false;
					ViewHelper.setAlpha(alphaView, 1.0f);
				}
			}
			if (!up) {
				int bottom = stayView.getBottom();
				int DIS = bottom-stayView.getHeight()-stayView.getHeight();
				if (y <= DIS) {
					System.out.println("gone----y------top----marginTop------" + y + " " + bottom + " " + stayView.getHeight() + stayView.getHeight());
					stayViewListener.onStayViewGone();
					up = true;
					ViewHelper.setAlpha(alphaView, 0.0f);
				}
			}
		}
	}

	public interface StayViewListener {
		public void onStayViewShow();

		public void onStayViewGone();
	}

}
