package com.android.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 虚线 
 * 
 * @author 孟祥程
 *
 */

public class DashedLineView extends View {

	// 虚线颜色
	int color = Color.GRAY;

	//实心空心
	Style style = Paint.Style.STROKE;

	public DashedLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DashedLineView(Context context, AttributeSet attrs, int color) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	public DashedLineView(Context context, AttributeSet attrs, int color,Style style) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.color = color;
		this.style = style;
	}

	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();

		paint.setStyle(style);

		paint.setColor(color);

		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(this.getWidth(), 0);

		//PathEffect是用来控制绘制轮�?线条)的方�?
		PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);

	}
}
