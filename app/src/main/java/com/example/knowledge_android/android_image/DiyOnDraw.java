package com.example.knowledge_android.android_image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DiyOnDraw extends androidx.appcompat.widget.AppCompatTextView {


    public DiyOnDraw(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.RED); //指定颜色
        shapeDrawable.setBounds(10,10,200,100);//指定位置

        //画绿色背景
        canvas.drawColor(Color.GREEN);

        //画椭圆
        shapeDrawable.draw(canvas); //将自己画到画布上
    }
}
