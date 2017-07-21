package bwie.com.myfate.MyActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


import bwie.com.myfate.R;
import bwie.com.myfate.bean.UploadPhotoBean;
import bwie.com.myfate.md5.core.JNICore;
import bwie.com.myfate.md5.core.SortUtils;
import bwie.com.myfate.network.BaseObserver;
import bwie.com.myfate.network.RetrofitManager;
import bwie.com.myfate.utils.ImageResizeUtils;
import bwie.com.myfate.utils.SDCardUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static bwie.com.myfate.utils.ImageResizeUtils.copyStream;


@RuntimePermissions
public class PhotoActivity extends Activity implements View.OnClickListener {


    private int width;
    private int height;
    private String img_path;

    private ImageView headPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_third);
        headPortrait = (ImageView) findViewById(R.id.headPortrait);
        findViewById(R.id.my_camera).setOnClickListener(this);
        findViewById(R.id.my_pick).setOnClickListener(this);

    }

    static final int INTENTFORCAMERA = 1;
    static final int INTENTFORPHOTO = 2;

    //获取时间戳 生成名字
    public String LocalPhotoName;

    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "face.jpg";
        return LocalPhotoName;
    }

    /**
     * 打开系统相机
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void toCamera() {
        try {
            Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
            startActivityForResult(intentNow, INTENTFORCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void showRationaleForCamera(final PermissionRequest request) {

        new AlertDialog.Builder(this)
                .setMessage("需要打开您的相机来上传照片并保存照片")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        request.proceed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void onDenied() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

    }


    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void onNeverAsyAgain() {
        Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
    }


    /**
     * 打开相册
     */
    public void toPhoto() {
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void toCheckPermissionCamera() {

        PhotoActivityPermissionsDispatcher.toCameraWithCheck(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case INTENTFORPHOTO:
                //相册

                try {
                    // 必须这样处理，不然在4.4.2手机上会出问题
                    Uri originalUri = data.getData();
                    File f = null;
                    if (originalUri != null) {
                        f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                        if (null == actualimagecursor) {
                            if (originalUri.toString().startsWith("file:")) {
                                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                if (!file.exists()) {
                                    //地址包含中文编码的地址做utf-8编码
                                    originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                }
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }
                        } else {
                            // 系统图库
                            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            actualimagecursor.moveToFirst();
                            img_path = actualimagecursor.getString(actual_image_column_index);
                            if (img_path == null) {
                                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            } else {
                                File file = new File(img_path);
                                FileInputStream inputStream = new FileInputStream(file);
                                FileOutputStream outputStream = new FileOutputStream(f);
                                copyStream(inputStream, outputStream);
                            }

                        }
                        Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), 720);
                        FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                        if (bitmap != null) {
                            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                                fos.close();
                                fos.flush();
                            }
                            if (!bitmap.isRecycled()) {
                                bitmap.isRecycled();
                            }

                            width = bitmap.getWidth();
                            height = bitmap.getHeight();

                            uploadFile(f);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
                try {
                    Uri originalUri = data.getData(); // 获得图片的uri

                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                    headPortrait.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 100));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                    // 显得到bitmap图片
                    // imageView.setImageBitmap(bm);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    // 好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    // 按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case INTENTFORCAMERA:
//                相机
                try {

                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), 720);
                    System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }
                        width = bitmap.getWidth();
                        height = bitmap.getHeight();
                        headPortrait.setImageBitmap(bitmap);
                        uploadFile(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void uploadFile(File file) {

        if (!file.exists()) {
            Toast.makeText(this, " 照片不存在", Toast.LENGTH_SHORT);
            return;
        }
        String[] arr = file.getAbsolutePath().split("/");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        long ctimer = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", ctimer + "");

        map.put("user.picWidth", width + "");
        map.put("user.picHeight", height + "");

        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);

        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("image", arr[arr.length - 1], requestFile)
                .build();


        RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
                    if (bean.getResult_code() == 200) {
                        Toast.makeText(PhotoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();



                        Intent intent=new Intent(PhotoActivity.this,Main.class);
                        startActivity(intent);
                    }
                    //   AppManager.getAppManager().finishActivity(PhotoActivity.this);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailed(int code) {
                Toast.makeText(PhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //拍照
            case R.id.my_camera:
                toCheckPermissionCamera();
                break;
            //本地上传
            case R.id.my_pick:
                toPhoto();
                break;
        }
    }
}
