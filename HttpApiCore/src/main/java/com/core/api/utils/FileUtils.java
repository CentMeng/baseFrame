package com.core.api.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件相关
 * 缓存目录直接调用getDiskFileDir 或者 getDiskCacheDir
 * @author mengxc
 */
public class FileUtils {

    public static String SDPATH;

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        //得到当前外部存储设备的目录  
        SDPATH = Environment.getExternalStorageDirectory() + "/";
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
            // || !Environment.isExternalStorageRemovable()
                ) {
            try {
                SDPATH = Environment.getExternalStorageDirectory() + "/";
            } catch (Exception e) {
                SDPATH = Environment.getRootDirectory() + "/";
            }
        } else {
            SDPATH = Environment.getRootDirectory() + "/";
        }

    }

    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }


    public boolean isFileExist(String fileName) {

        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public File write2SDFromInput(String path, String fileName, InputStream inputStream) {
        // TODO Auto-generated method stub  
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((inputStream.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 获取缓存路径地址
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
            // || !Environment.isExternalStorageRemovable()
                ) {
            try {
                ///sdcard/Android/data/<application package>/cache
                //getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据  files对应设置中的清楚数据
                //通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据  cache对应设置中的清楚缓存
                cachePath = context.getExternalCacheDir().getPath();
            } catch (Exception e) {
                //getCacheDir()方法用于获取/data/data/<application package>/cache目录
                //getFilesDir()方法用于获取/data/data/<application package>/files目录
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 创建和获取缓存路径 
     *
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = getDiskCachePath(context);

        File file = new File(cachePath + File.separator + uniqueName);
        file.mkdirs();
        return file;
    }

    /**
     * 获取缓存文件地址
     * @param context
     * @return
     */
    public static String getDiskFilePath(Context context,String type){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
            // || !Environment.isExternalStorageRemovable()
                ) {
            try {
                ///sdcard/Android/data/<application package>/cache
                //getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据  files对应设置中的清楚数据
                //通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据  cache对应设置中的清楚缓存
                if (TextUtils.isEmpty(type)) {
                    type = Environment.DIRECTORY_PICTURES;
                }
                cachePath = context.getExternalFilesDir(type).getPath();
            } catch (Exception e) {
                //getCacheDir()方法用于获取/data/data/<application package>/cache目录
                //getFilesDir()方法用于获取/data/data/<application package>/files目录
                cachePath = context.getFilesDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 创建和获取数据保存路径 
     *
     * @param context
     * @param uniqueName 后缀文件夹
     * @param type       文件类型，空则默认
     * @return
     */
    public static File getDiskFileDir(Context context, String uniqueName, String type) {

        String cachePath = getDiskFilePath(context,type);
        File file = new File(cachePath);
        if(!TextUtils.isEmpty(uniqueName)){
             file = new File(cachePath + File.separator + uniqueName);
        }
        file.mkdirs();
        return file;
    }

}
