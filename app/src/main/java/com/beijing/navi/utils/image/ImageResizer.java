package com.beijing.navi.utils.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.core.utils.phone.LogUtils;

import java.io.FileDescriptor;

/**
 * @author CentMeng csdn@vip.163.com on 16/3/11.
 *         图片压缩
 * 当加载图像时，可以设置BitmapFactory应该使用的采样大小。在BitmapFactory.Options中指定* *inSampleSize参数。例如，将inSampleSize = 8时，产生一幅图的大小是原始大小的1/8。要注意的是首先*应将BitmapFactoryOptions.inJustDecodeBounds变量设置为true,这将通知BitmapFactory类只需返*回该图像的范围，而无需尝试解码图像本身。最后将BitmapFactory.Options.inJustDecodeBounds设置为*false，最后对其进行真正的解码。
 */
public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {

    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        //First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        //First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        //Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        LogUtils.e(TAG, "ORIGIN,W =" + width + "h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //Calculate the largest inSampleSize value that is a power of 2
            //keeps both
            //height and width larger than the requested height and width
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
