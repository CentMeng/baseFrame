package com.beijing.navi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.core.utils.Bitmap.BitmapUtils;
import com.android.core.utils.Bitmap.Path;
import com.android.core.utils.File.FileUtils;
import com.android.volley.Response;
import com.core.api.ApiSettings;
import com.core.api.event.request.UploadTokenRequest;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

/**
 * @author CentMeng csdn@vip.163.com on 16/3/14.
 *         上传图片的基本类，验证七牛token
 */
public class UploadImageBaseActivity extends BaseActivity {

    Dialog choosePictureDialog;

    public boolean isCut = true;

    public static final int PHOTO_CUTBASEGRAPH = 5001;// 拍照
    public static final int PHOTO_CUTBASEZOOM = 5002; // 本地相册缩放
    public static final int PHOTO_BASEGRAPH = 5004;// 拍照不裁剪
    public static final int PHOTO_BASEZOOM = 5005; // 本地相册缩放不裁剪
    public static final int PHOTO_BASECUT = 5003; //  裁剪

    /**
     * 拍照的路径地址
     */
    public Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果uploadToken为空重新获取
        getToken();
    }

    @Override
    protected void onDestroy() {
        choosePictureDialog = null;
        super.onDestroy();
    }

    public void getToken() {
        getHttpClient().get(new UploadTokenRequest().success(tokenListener));
    }


    Response.Listener<com.core.api.event.response.Response> tokenListener = new Response.Listener<com.core.api.event.response.Response>() {
        @Override
        public void onResponse(com.core.api.event.response.Response response) {

            if (success(response)) {
                app.setUploadToken((String) response.getParam());
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case PHOTO_CUTBASEZOOM:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    startCrop(data.getData());

                    break;
                case PHOTO_BASECUT:
                    if ((data == null || data.getData() == null) && (photoUri == null || !com.android.core.utils.File.FileUtils.isFileExist(photoUri.getPath()))) {
                        return;
                    }
                    if(data == null || data.getData() == null){
                     //解决小米手机不能上传
                        asyncUpload(BitmapUtils.imgToByteArrayOutputStream(
                                photoUri.getPath()));
                    }else {
                        asyncUpload(BitmapUtils.imgToByteArrayOutputStream(
                                Path.getAbsoluteImagePath(data.getData(), this)));
                    }
                    if (choosePictureDialog != null && choosePictureDialog.isShowing()) {
                        choosePictureDialog.dismiss();
                    }

                    break;
                case PHOTO_CUTBASEGRAPH:
                    if ((data == null || data.getData() == null) && (photoUri == null ||!com.android.core.utils.File.FileUtils.isFileExist(photoUri.getPath()))) {
                        return;
                    }
                    startCrop(photoUri);
                    break;
                default:
                    break;
            }
    }

    public void asyncUpload(final ByteArrayOutputStream stream) {
        if (stream != null) {
            showLoadingDialog(true, "上传中");

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);// (0-100)压缩文件

            // String key = <指定七牛服务上的文件名，或 null>;
            String token = app.getUploadToken();

            try {
                app.getUploadManager().put(stream.toByteArray(), null, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
//                        Log.i("qiniu", ApiSettings.URL_IMAGE_BASE + key + ",\r\n " + info
//                                + ",\r\n " + res);
                                try {
                                    String url = ApiSettings.URL_IMAGE_BASE + res.getString("key");
                                    sucessUploadPic(url);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                      cancelLoadingDialog();
                                    showSystemShortToast("发生了指点君都搞不懂的错误，请重试");
                                    getToken();
                                }
                            }
                        }, null);
            } catch (Exception e) {
                e.printStackTrace();
                cancelLoadingDialog();
                showSystemShortToast("发生了指点君都搞不懂的错误，请重试");
            }
        }
    }


    public void asyncUpload(final String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            showLoadingDialog(true, "上传中");

//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);// (0-100)压缩文件

            // String key = <指定七牛服务上的文件名，或 null>;
            String token = app.getUploadToken();

            try {
                app.getUploadManager().put(filePath, null, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
//                        Log.i("qiniu", ApiSettings.URL_IMAGE_BASE + key + ",\r\n " + info
//                                + ",\r\n " + res);
                                try {
                                    String url = ApiSettings.URL_IMAGE_BASE + res.getString("key");
                                    sucessUploadPic(url);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    cancelLoadingDialog();
                                    showSystemShortToast("发生了指点君都搞不懂的错误，请重试");
                                    getToken();
                                }
                            }
                        }, null);
            } catch (Exception e) {
                e.printStackTrace();
                cancelLoadingDialog();
                showSystemShortToast("发生了指点君都搞不懂的错误，请重试");
            }
        }
    }



    public void sucessUploadPic(String url) {

    }

    protected void showChoosePictureDialog(boolean isCut) {
        this.isCut = isCut;
        if (choosePictureDialog == null) {
            View view = getLayoutInflater().inflate(R.layout.dialog_avatar, null);
            view.findViewById(R.id.cancel).setOnClickListener(dialogListener);

            choosePictureDialog = new Dialog(this, R.style.transparentFrameWindowStyle);
            choosePictureDialog.setContentView(view, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            choosePictureDialog.setCancelable(true);
            Window window = choosePictureDialog.getWindow();
            // 设置显示动画
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // 设置显示位置
            choosePictureDialog.onWindowAttributesChanged(wl);
            // 设置点击外围解散
            choosePictureDialog.setCanceledOnTouchOutside(true);
            choosePictureDialog.show();

            view.findViewById(R.id.takePhoto).setOnClickListener(dialogListener);
            view.findViewById(R.id.choosePhoto).setOnClickListener(dialogListener);
        } else {
            choosePictureDialog.show();
        }
    }

    View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancel:
                    choosePictureDialog.dismiss();
                    break;
                case R.id.takePhoto:
                    takePhoto();
                    break;
                case R.id.choosePhoto:
                    choosePicture();
                    break;
            }
        }
    };

    /**
     * 相册选择图片
     */
    protected void choosePicture() {
        Intent intent = new Intent(
                // 相册
                Intent.ACTION_PICK,
                null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if(isCut){
            startActivityForResult(intent, PHOTO_CUTBASEZOOM);
        }else{
            startActivityForResult(intent, PHOTO_BASEZOOM);
        }
        if (choosePictureDialog != null && choosePictureDialog.isShowing()) {
            choosePictureDialog.cancel();
        }
    }


    /**
     * 照相
     */
    protected void takePhoto() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            File file = FileUtils.getDiskCacheDir(this, "tempPic");

            file = new File(file.getPath(), getPicName());

            if (file != null) {
                this.photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                if(isCut){
                    startActivityForResult(openCameraIntent, PHOTO_CUTBASEGRAPH);
                }else{
                    startActivityForResult(openCameraIntent, PHOTO_BASEGRAPH);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (choosePictureDialog != null && choosePictureDialog.isShowing()) {
            choosePictureDialog.cancel();
        }
    }

    protected String getPicName() {
        String address = "" + UUID.randomUUID() + ".JPEG".toString().trim();
        return address;

    }


    /**
     * @param photoUri 相册照片uri
     */
    void startCrop(Uri photoUri) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            File file = FileUtils.getDiskCacheDir(this, "tempPic");
            file = new File(file.getPath() + "/" + getPicName());
            Uri imageUri =Uri.fromFile(file);

            final Intent intent = new Intent("com.android.camera.action.CROP");


            intent.setDataAndType(photoUri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 500);
            intent.putExtra("outputY", 500);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            this.photoUri = imageUri;
            // 输出格式
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, PHOTO_BASECUT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
