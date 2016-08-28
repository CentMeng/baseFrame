package com.beijing.navi.utils;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;

import java.io.File;
import java.io.IOException;

/**
 * @author CentMeng csdn@vip.163.com on 15/11/16.
 *         七牛上传配置
 */
public class UploadUtil {

    public static String DIR_PATH = "folk";

    public static UploadManager getUploadManager() {
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(DIR_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }


     //默认使用 key 的url_safe_base64编码字符串作为断点记录文件的文件名。
     //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };

    // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
    //UploadManager uploadManager = new UploadManager(recorder);  // 1
    //UploadManager uploadManager = new UploadManager(recorder, keyGen); // 2
   // 或 在初始化时指定：
        Configuration config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .recorder(recorder, keyGen)
                .build();

        return new UploadManager(config);
    }
}
