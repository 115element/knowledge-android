package com.donkingliang.labels.improve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextViewImprove extends androidx.appcompat.widget.AppCompatTextView {

    //右上提示信息(不完美)
    private String topLeftWarn;// = "+¥3";

    // Shared Paint
    private static TextPaint qtyPaint;

    static {
        qtyPaint = new TextPaint();
        qtyPaint.setAntiAlias(true);
        qtyPaint.setTextSize(20);
    }


    public TextViewImprove(@NonNull Context context) {
        this(context, null);
    }

    public TextViewImprove(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewImprove(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    //绘制边框的左右外边距
    private final int marginLeftRight = 5;
    //绘制边框的上下外边距
    private final int marginTopBottom = 13;

    //默认边框的颜色黑色
    private final int stroke_color = Color.BLACK;

    private void init() {
        this.setPadding(5, 2, 5, 2);
        this.setTextSize(8);
        //绘制文字的颜色
        this.setTextColor(stroke_color);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();


        /////////////////////////////以下是绘制右上提示/////////////////////////////////////////////////
        if (!TextUtils.isEmpty(topLeftWarn)) {
            int centerX = width - marginLeftRight - 20;
            int centerY = marginTopBottom + 5;

            ////画红色圆
            qtyPaint.setColor(Color.RED);
            canvas.drawCircle(centerX, centerY, 18, qtyPaint);
            ////写白色字
            qtyPaint.setColor(Color.WHITE);
            qtyPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(topLeftWarn, centerX, centerY, qtyPaint);
        }


        //////////////////////以下是自定义绘制组件内边框//////////////////////////////////////////////////
        this.setTextColor(stroke_color);
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //  将边框设为黑色
        paint.setColor(stroke_color);
        paint.setStrokeWidth(2);
        int left = marginLeftRight;
        int right = width - marginLeftRight;
        int top = marginTopBottom;
        int bottom = height - marginTopBottom;

        //left      x坐标(左边的x坐标)
        //top       y坐标(上边的y坐标)
        //right     x坐标(右边的x坐标)
        //bottom    y坐标(下边的y坐标)
        //RectF rect = new RectF(5, 15, 80, 60);
        @SuppressLint("DrawAllocation") RectF rect = new RectF(left, top, right, bottom);

        //rx：x方向上的圆角半径。
        //ry：y方向上的圆角半径
        canvas.drawRoundRect(rect, 10, 10, paint);
        super.onDraw(canvas);
    }

}
