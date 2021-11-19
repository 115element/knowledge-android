package com.example.knowledge_android.android_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.io.FileNotFoundException;

public class BitMapTestActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void tt() {
        //需求1：加载资源文件中的图片并显示
        imageView.setImageResource(R.drawable.plu);

        //需求2：加载存储控件中的图片资源并显示
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/sdcard/ic_plu.png");
        imageView.setImageBitmap(bitmap);
    }

    public void saveImage() throws FileNotFoundException {
        //需求3：将一个bitmap对象保存到存储空间
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/sdcard/ic_plu.png");

        //保存的位置是 /data/data/应用/file下
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput("ic_plu.png", Context.MODE_PRIVATE));
    }
}
