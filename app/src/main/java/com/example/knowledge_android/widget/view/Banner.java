package com.example.knowledge_android.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.knowledge_android.knowledge.GUtil;


public class Banner extends AppCompatTextView {

    /**
     * Model object.
     */
    String modelObject;

    /**
     * Property name of the model object.
     */
    String property;

    /**
     * Title string on the left.
     */
    String title = "";

    /**
     * Auto size minimum text size in pixels.
     */
    int autoSizeMinTextSize;

    /**
     * Auto size maximum text size in pixels.
     */
    int autoSizeMaxTextSize;

    boolean paintBorder;

    String borderWidth = "8dp";

    int borderColor = Color.argb(200, 21, 130, 178);

    private boolean hasSetAutoSize;

    Banner(Context context) {
        super(context);
    }


    String getRenderedText() {
        String propertyValue = modelObject != null && property != null ? modelObject : property;
        return title + propertyValue;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence oldText = getText();
        String newText = getRenderedText();
        if (newText != oldText) {
            setText(newText); // refresh to up-to-dated value
        }

        if (!hasSetAutoSize && autoSizeMinTextSize > 0 && autoSizeMaxTextSize > 0) {
            setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize,
                    1, TypedValue.COMPLEX_UNIT_PX);
            hasSetAutoSize = true;
        }

        // Alex: 绘制边框
        if (paintBorder) {
            // 创建画笔
            Paint paint = new Paint();

            // 设置画笔的各个属性
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth((float) stringToPixels(borderWidth));

            // 抗锯齿
            paint.setAntiAlias(true);

            // 创建矩形框
            Rect rect = new Rect(0, 0, getWidth(), getHeight());

            // 绘制边框
            canvas.drawRect(rect, paint);
        }

        super.onDraw(canvas);
    }


    static int stringToPixels(String value) {
        try {
            int pixels;
            if (value.endsWith("dp")) {
                String dip = value.replace("dp", "");
                Float dip1 = new Float(dip);
                pixels = GUtil.dip2px(dip1);
            } else if (value.endsWith("sp")) {
                String sp = value.replace("sp", "");
                Float sp1 = new Float(sp);
                pixels = GUtil.sp2px(sp1);
            } else if (value.endsWith("px")) {
                String sp = value.replace("px", "");
                pixels = Integer.parseInt(sp);
            } else {
                pixels = Integer.parseInt(value);
            }
            return pixels;
        } catch (Exception ignored) {
            return 0;
        }
    }
}
