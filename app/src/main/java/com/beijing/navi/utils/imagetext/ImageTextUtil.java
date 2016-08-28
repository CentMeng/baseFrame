package com.beijing.navi.utils.imagetext;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

import com.android.core.utils.Text.StringUtils;
import com.android.core.view.git.GlideImageGetter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CentMeng on 16/6/1.
 */
public class ImageTextUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static Drawable getUrlDrawable(String source,  TextView mTextView) {
        GlideImageGetter imageGetter = new GlideImageGetter(mTextView.getContext().getApplicationContext(),mTextView);
        return imageGetter.getDrawable(source);

    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private static int calculateHeight(int width, Bitmap bitmap) {

        float bwinth = bitmap.getWidth();
        float bheight = bitmap.getHeight();

        float scale = bwinth / bheight;

        return (int) (width / scale);
    }
    public static void setImageText(TextView tv, String html, int width, boolean isShow){
        setImageText(tv,html,width,isShow,false);
    }
    public static void setImageText(TextView tv, String html, int width, boolean isShow,boolean isUTF8){
        if(isShow && !TextUtils.isEmpty(tv.getText().toString())){
            return;
        }
        if(isUTF8){
            html = StringUtils.getDecodeBodyHtml(html);
        }

        Spanned htmlStr = Html.fromHtml(html);

        //各别机型如魅族手机，如果使用一下语句会报错 View too large to fit into drawing cache 的Warning
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null); 关闭硬件加速
//            tv.setTextIsSelectable(true); 设置是否可以粘贴
//        }
        tv.setText(htmlStr);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if(text instanceof Spannable){
            int end = text.length();
            Spannable sp = (Spannable)tv.getText();
            URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);
            ImageSpan[] imgs = sp.getSpans(0,end,ImageSpan.class);
            StyleSpan[] styleSpens = sp.getSpans(0,end,StyleSpan.class);
            ForegroundColorSpan[] colorSpans = sp.getSpans(0,end,ForegroundColorSpan.class);
            SpannableStringBuilder style=new SpannableStringBuilder(text);
            style.clearSpans();
            for(URLSpan url : urls){
                style.setSpan(url,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(isShow){
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF12ADFA"));
                    style.setSpan(colorSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            if(isShow){
                Pattern pattern = Pattern
                        .compile("(http://|https://|www){1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn)[^\u4e00-\u9fa5\\s]*");
                Matcher matcher = pattern.matcher(htmlStr);
                while (matcher.find()) {
                    String href = matcher.group(0);
                    if(!StringUtils.isStartWithHttp(href)){
                        href = StringUtils.addHttp(href);
                    }
                    URLSpan url = new URLSpan(href);
                    style.setSpan(url,matcher.start(),matcher.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF12ADFA"));
                    style.setSpan(colorSpan,matcher.start(),matcher.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            for(ImageSpan url : imgs){
                ImageSpan span = new ImageSpan(getUrlDrawable(url.getSource(),tv),url.getSource());
                style.setSpan(span,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            for(StyleSpan styleSpan : styleSpens){
                style.setSpan(styleSpan,sp.getSpanStart(styleSpan),sp.getSpanEnd(styleSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            for(ForegroundColorSpan colorSpan : colorSpans){
                style.setSpan(colorSpan,sp.getSpanStart(colorSpan),sp.getSpanEnd(colorSpan),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            tv.setText(style);
        }
    }
}
