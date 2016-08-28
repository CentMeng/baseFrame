package com.beijing.navi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/***
 * @title  圆角ImageView
 */
public class RoundImageViewLeft extends ImageView {

	private Paint paint;
	private int mAdius = 6;
	private Paint paint2;

	public RoundImageViewLeft(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public RoundImageViewLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundImageViewLeft(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {


		float density = context.getResources().getDisplayMetrics().density;
		mAdius = (int) (mAdius * density);

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		paint2 = new Paint();
		paint2.setXfermode(null);
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap);
		super.draw(canvas2);
		drawLiftUp(canvas2);
		drawLiftDown(canvas2);
		canvas.drawBitmap(bitmap, 0, 0, paint2);
		bitmap.recycle();
	}

	private void drawLiftUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, mAdius);
		path.lineTo(0, 0);
		path.lineTo(mAdius, 0);
		path.arcTo(new RectF(
						0,
						0,
						mAdius * 2,
						mAdius * 2),
				-90,
				-90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLiftDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getHeight() - mAdius);
		path.lineTo(0, getHeight());
		path.lineTo(mAdius, getHeight());
		path.arcTo(new RectF(
						0,
						getHeight() - mAdius * 2,
						0 + mAdius * 2,
						getHeight()),
				90,
				90);
		path.close();
		canvas.drawPath(path, paint);
	}

}