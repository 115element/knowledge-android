package com.example.knowledge_android.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DashLineView extends View {

    private Paint mPaint;
    private Path mPath;
    private int lineColor;

    public DashLineView(Context context, AttributeSet attributeSet) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        float[] floats = new float[]{15f,5f};
        mPaint.setPathEffect(new DashPathEffect(floats, 0));

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerY = getHeight() / 2;
        mPaint.setColor(lineColor);
        mPath.reset();
        mPath.moveTo(0, centerY);
        mPath.lineTo(getWidth(), centerY);
        canvas.drawPath(mPath, mPaint);
    }

//    // 其它方法还是不变
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        // 前四个参数没啥好讲的，就是起点和终点而已。
//        // color数组的意思是从透明 -> 黑 -> 黑 -> 透明。
//        // float数组与color数组对应：
//        // 0 -> 0.3 (透明 -> 黑)
//        // 0.3 - 0.7 (黑 -> 黑，即不变色)
//        // 0.7 -> 1 (黑 -> 透明)
//        mPaint.setShader(new LinearGradient(0, 0, getWidth(), 0,
//                [Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.TRANSPARENT] as int[],
//                [0, 0.3f, 0.7f, 1f] as float[], Shader.TileMode.CLAMP))
//    }

    void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
}
