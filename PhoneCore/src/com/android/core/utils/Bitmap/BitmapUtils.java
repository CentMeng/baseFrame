package com.android.core.utils.Bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import com.android.core.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author CentMeng csdn@vip.163.com on 15/11/3.
 */
public class BitmapUtils {



    /**
     * 质量压缩方法
     *
     * @param image
     * @param size  大小Kb
     * @return
     */
    public static ByteArrayOutputStream compressImageReturnByte(Bitmap image, int size, int percent) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size && options >= 10) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            LogUtils.e("长度", "" + baos.toByteArray().length);
            baos.reset();//重置baos即清空baos
            options -= percent;//每次都减少percent
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        return baos;
    }


    /**
     * 新
     *
     * @param path
     * @return
     */


    public static Bitmap getBitmapFromUri(String path) {

        return BitmapFactory.decodeStream(new ByteArrayInputStream(imgToByteArrayOutputStream(path).toByteArray()));
    }

    /**
     * 获取bitmap通过路径不进行压缩
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromUriNoDeal(String path) {
        return BitmapFactory.decodeFile(path);
    }


    public static int max = 0;

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // Bitmap btBitmap=BitmapFactory.decodeFile(path);
        // System.out.println("原尺寸高度："+btBitmap.getHeight());
        // System.out.println("原尺寸宽度："+btBitmap.getWidth());
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 800)
                    && (options.outHeight >> i <= 800)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        // 当机型为三星时图片翻转
//		bitmap = Photo.photoAdapter(path, bitmap);
//		System.out.println("-----压缩后尺寸高度：" + bitmap.getHeight());
//		System.out.println("-----压缩后尺寸宽度度：" + bitmap.getWidth());
        return bitmap;
    }


    /**
     * 创建圆角图片
     *
     * @param x              图像的宽度
     * @param y              图像的高度
     * @param image          源图片
     * @param outerRadiusRat 圆角的大小
     * @return 圆角图片
     */
    public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
        // 根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(image);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, x, y);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        // 将源图片绘制到这个圆角矩形上
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }


    /**
     * Img to input stream input stream.
     *
     * @param path 本地图片的绝对路径，比如sdcard/aa/xx.png
     * @return input stream
     * @description 把本地图片压缩后再转换为ByteArrayOutputStream
     */
    public static ByteArrayOutputStream imgToByteArrayOutputStream(String path) {
        int orientation = 0;
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 开始读入图片把options.inJustDecodeBounds设为true
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
        float hh = 1200f;// 这里设置高度为800f
        float ww = 720f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(path, newOpts);
        os.reset();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        int ps = 100;// 压缩率
        if (os.toByteArray().length / 1024 > 128) {
            ps = 20;
        }
        if (os.toByteArray().length / 1024 > 256) {
            ps = 40;
        }

        if (os.toByteArray().length / 1024 > 512) {
            ps = 60;
        }

        if (os.toByteArray().length / 1024 > 1024) {
            ps = 80;
        }
        os.reset();
        bitmap.compress(Bitmap.CompressFormat.JPEG, ps, os);
        int options = 100;
        while (os.toByteArray().length / 1024 > 250 && options > 0) { //
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            os.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);//
            // 这里压缩options%，把压缩后的数据存放到baos中
            options -= 20;// 每次都减少20
        }
        if (degree > 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap != null) {
                os.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
        }
        recycleBitmap(bitmap);

//        return new ByteArrayInputStream(os.toByteArray());
        return os;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }


}
