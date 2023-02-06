package com.example.graduatedesign.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatedesign.R;
import com.example.graduatedesign.bean.User;
import com.example.graduatedesign.utils.BitmapUtils;
import com.example.graduatedesign.utils.CameraUtils;
import com.example.graduatedesign.utils.SPUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyActivity extends AppCompatActivity {

    private LinearLayout head;
    //是否拥有权限
    private boolean hasPermissions = false;
    //存储拍完照后的图片
    private File outputImagePath;

    ImageView imageView;
    //启动相机标识
    public static final int TAKE_PHOTO = 1;
    //启动相册标识
    public static final int SELECT_PHOTO = 2;
    //底部弹窗
    private BottomSheetDialog bottomSheetDialog;
    //弹窗视图
    private View bottomView;
    //拍照和相册获取图片的Bitmap
    private Bitmap orc_bitmap;


    @SuppressLint("MissingInflatedId")

    //Glide请求图片选项配置
    private RequestOptions requestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_my);
        head=findViewById(R.id.head);
        imageView=findViewById(R.id.image);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image=findViewById(R.id.image);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog = new BottomSheetDialog(MyActivity.this);
                        bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
                        bottomSheetDialog.setContentView(bottomView);
                        bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
                        TextView tvTakePictures = bottomView.findViewById(R.id.tv_take_pictures);
                        TextView tvOpenAlbum = bottomView.findViewById(R.id.tv_open_album);
                        TextView tvCancel = bottomView.findViewById(R.id.tv_cancel);

                        //拍照
                        tvTakePictures.setOnClickListener(v -> {
                            takePhoto();
                            bottomSheetDialog.cancel();
                        });
                        //打开相册
                        tvOpenAlbum.setOnClickListener(v -> {
                            openAlbum();
                            bottomSheetDialog.cancel();
                        });
                        //取消
                        tvCancel.setOnClickListener(v -> {
                            bottomSheetDialog.cancel();
                        });
                        //底部弹窗显示
                        bottomSheetDialog.show();
                    }
                });
            }
        });

        //取出缓存
        String imageUrl = SPUtils.getString("imageUrl",null,this);
        if(imageUrl != null){
            Glide.with(this).load(imageUrl).apply(requestOptions).into(imageView);
        }

    }

    private void checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            RxPermissions rxPermissions = new RxPermissions(this);
            //权限请求
            rxPermissions
                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {//申请成功
                            hasPermissions = true;
                        } else {//申请失败
                            hasPermissions = false;
                        }
                    });
        } else {
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        if (!hasPermissions) {
            checkVersion();
            return;
        }
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        outputImagePath = new File(this.getExternalCacheDir(),
                filename + ".jpg");
        Intent takePhotoIntent = CameraUtils.getTakePhotoIntent(this, outputImagePath);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        if (!hasPermissions) {
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.getSelectPhotoIntent(), SELECT_PHOTO);
    }

    /**
     * 返回到Activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //拍照后返回
            case TAKE_PHOTO:
                if (resultCode == -1) {
                    //显示图片
                    displayImage(outputImagePath.getAbsolutePath());
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == -1) {
                    String imagePath = null;
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4及以上系统使用这个方法处理图片
                        imagePath = CameraUtils.getImageOnKitKatPath(data, this);
                    } else {
                        imagePath = CameraUtils.getImageBeforeKitKatPath(data,this);
                    }

                    //放入缓存
                    SPUtils.putString("imageUrl",imagePath,this);

                    //显示图片
                    displayImage(imagePath);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private void displayImage(String imagePath) {

        if (!TextUtils.isEmpty(imagePath)) {

            //放入缓存
            SPUtils.putString("imageUrl",imagePath,this);

            final User user=new User();
            user.setImage(imagePath);

            //显示图片
            Glide.with(this).load(imagePath).apply(requestOptions).into(imageView);

            //压缩图片
            orc_bitmap = CameraUtils.compression(BitmapFactory.decodeFile(imagePath));
            //转Base64
            //Base64
            String base64Pic = BitmapUtils.bitmapToBase64(orc_bitmap);

        } else {
        }
    }

}