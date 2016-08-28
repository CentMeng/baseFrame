package com.beijing.navi.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;

import com.beijing.navi.R;


/**
 * textview fromhtml 时候加载图片使用
 * @author CentMeng csdn@vip.163.com on 16/6/2.
 */

public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;
    @SuppressWarnings("deprecation")
    public URLDrawable(Context context) {
        this.setBounds(getDefaultImageBounds(context));
        drawable = context.getResources().getDrawable(R.mipmap.notus);
        drawable.setBounds(getDefaultImageBounds(context));
    }
    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    @SuppressWarnings("deprecation")
    public Rect getDefaultImageBounds(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = (int) (width * 3 / 4);
        Rect bounds = new Rect(0, 0, width, height);
        return bounds;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}