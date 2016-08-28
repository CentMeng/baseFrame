package com.android.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
/***
 * @title  圆角ImageView
 */
public class RoundImageView extends ImageView {

	private final RectF mRound = new RectF();
	private float mAdius = 6;
	private final Paint mMaskPaint = new Paint();
	private final Paint mZonePaint = new Paint();

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundImageView(Context context) {
		super(context);
		init();
	}

	protected void init() {
		mMaskPaint.setAntiAlias(true);
		mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		mZonePaint.setAntiAlias(true);
		mZonePaint.setColor(Color.WHITE);
		float density = getResources().getDisplayMetrics().density;
		mAdius = mAdius * density;
	}

	public void setRectAdius(float adius) {
		mAdius = adius;
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		mRound.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(mRound, mZonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(mRound, mAdius, mAdius, mZonePaint);
		canvas.saveLayer(mRound, mMaskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.restore();
	}

}