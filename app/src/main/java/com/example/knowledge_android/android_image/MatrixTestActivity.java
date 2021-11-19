package com.example.knowledge_android.android_image;

import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MatrixTestActivity extends AppCompatActivity {

    ShapeDrawable shapeDrawable;

    private ImageView imageView;

    //使用该类，那么XML中的ImageView的scaleType必须指定为matrix
    private Matrix matrix = new Matrix();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void scaleBitmap(View view){
        //保存缩放比例
        matrix.postScale(0.5f,0.5f);
        //将matrix设置到ImageView
        imageView.setImageMatrix(matrix);
    }

    public void rotateBitmap(View view){
        matrix.postRotate(140); //旋转
        imageView.setImageMatrix(matrix);
    }

    public void translateBitmap(View view){
        matrix.postTranslate(10,10); //平移
        imageView.setImageMatrix(matrix);
    }

    public void clearMatrix(View view){
        matrix.reset(); //清空重置
        imageView.setImageMatrix(matrix);//还原初始状态
    }
}
