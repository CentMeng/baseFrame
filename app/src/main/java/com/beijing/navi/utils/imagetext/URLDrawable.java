package com.beijing.navi.utils.imagetext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;

import com.beijing.navi.R;


/**
 * Created by qiuzhenhuan on 16/5/31.
 */
public class URLDrawable extends BitmapDrawable {
    protected Bitmap bitmap;

    public URLDrawable(Context context) {
        this.setBounds(getDefaultImageBounds(context));
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.notus);
        bitmap = drawable.getBitmap();
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }

    @SuppressWarnings("deprecation")
    public Rect getDefaultImageBounds(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = (int) (width * 3 / 4);
        Rect bounds = new Rect(0, 0, width, height);
        return bounds;
    }
}