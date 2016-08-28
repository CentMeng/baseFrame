package com.beijing.navi.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import java.net.URL;

/**
 * textview fromhtml 时候加载图片使用
 * @author CentMeng csdn@vip.163.com on 16/6/2.
 */

public class URLImageParser implements ImageGetter {
    TextView textView;
    Context context;
    int width;
    public URLImageParser(Context contxt, TextView textView,int width) {
        this.context = contxt;
        this.textView = textView;
        this.width = width;
    }

    @Override
    public Drawable getDrawable(String paramString) {
        URLDrawable urlDrawable = new URLDrawable(context);

        ImageGetterAsyncTask getterTask = new ImageGetterAsyncTask(urlDrawable);
        getterTask.execute(paramString);
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable drawable) {
            this.urlDrawable = drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                urlDrawable.drawable = result;

                URLImageParser.this.textView.requestLayout();
            }
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        public Drawable fetchDrawable(String url) {
            Drawable drawable = null;
            URL Url;
            try {
                Url = new URL(url);
                drawable = Drawable.createFromStream(Url.openStream(), "");
            } catch (Exception e) {
                return null;
            }
//按比例缩放图片
//            Rect bounds = getDefaultImageBounds(context);
//            int newwidth = bounds.width();
//            int newheight = bounds.height();
//            double factor = 1;
//            double fx = (double)drawable.getIntrinsicWidth() / (double)newwidth;
//            double fy = (double)drawable.getIntrinsicHeight() / (double)newheight;
//            factor = fx > fy ? fx : fy;
//            if (factor < 1) factor = 1;
//            newwidth = (int)(drawable.getIntrinsicWidth() / factor);
//            newheight = (int)(drawable.getIntrinsicHeight() / factor);
            float bwinth = drawable.getIntrinsicWidth();
            float bheight = drawable.getIntrinsicHeight();

            float scale = bwinth / bheight;

            int newheight = (int) (width / scale) ;
            drawable.setBounds(0, 0, width, newheight);
            return drawable;
        }
    }

//    //预定图片宽高比例为 4:3
//    @SuppressWarnings("deprecation")
//    public Rect getDefaultImageBounds(Context context) {
//        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();
//        int height = (int) (width * 3 / 4);
//        Rect bounds = new Rect(0, 0, width, height);
//        return bounds;
//    }
}