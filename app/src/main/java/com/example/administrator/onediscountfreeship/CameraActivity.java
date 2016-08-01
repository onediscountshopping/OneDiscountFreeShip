package com.example.administrator.onediscountfreeship;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    //定义整型变量
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_OK = -1;
    //声明控件
    private ImageView imageView;
    //定义图片地址
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //获得控件
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    //拍照事件（添加点击事件）
    public void onClick_camera(View view) {
        //获得一个intent
        Intent intent = new Intent();
        //调用系统照相机
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //指明图片
        File imgFile = getFile(CameraActivity.this);
        //获取图片路径（绝对路径）
        imagePath = imgFile.getAbsolutePath();
        //key：要干什么 value：Uri对象，指明路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        //执行intent
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    //指明图片的路径
    public File getFile(Context context) {
        String imgPath = "";
        //判断SDCard是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SDCard里的存储路径 /mnt/sdcard/android/data/包名/cache
            imgPath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            //data/data/包名/cache/...
            imgPath = context.getCacheDir().getAbsolutePath();
        }
        return new File(imgPath, System.currentTimeMillis() + "jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断返回码
        if (requestCode == REQUEST_OK) {
            //判断请求码
            if (requestCode == REQUEST_CAMERA) {
                if (data != null) {
                    //获得bitmap
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //给控件添加所要展示的图片
                    imageView.setImageBitmap(bitmap);
                }
//                //获得bitmap
//                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                //给控件添加所要展示的图片
//                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
