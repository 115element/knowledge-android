package com.example.knowledge_android.widget.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.knowledge_android.R;


/**
 * 作者:zt
 * 时间:on 18/12/26
 * 说明: 自定义 按钮, 实现不同角的圆角 ,添加边框
 */
public class ZButton extends AppCompatButton {
    //形状 图
    private GradientDrawable shapeDrawable;

    private int bgColor;
    private float radius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    private int borderWidth;
    private int borderColor;


    public ZButton(Context context) {
        super(context);
    }

    public ZButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZTButton);
        if (a != null) {
            //背景颜色
            int bgColor = a.getColor(R.styleable.ZTButton_bgColor, 0);
            setBgColor(bgColor);
            //获取形状
            int shape = a.getInteger(R.styleable.ZTButton_shape, 0);
            setShape(shape);
            //获取圆角
            float radius = a.getFloat(R.styleable.ZTButton_radius, 0);
            setRadius(radius);
            //获取左上圆角
            float topLeftRadius = a.getFloat(R.styleable.ZTButton_topLeftRadius, 0);
            //获取右上圆角
            float topRightRadius = a.getFloat(R.styleable.ZTButton_topRightRadius, 0);
            //获取左下圆角
            float bottomLeftRadius = a.getFloat(R.styleable.ZTButton_bottomLeftRadius, 0);
            //获取右下圆角
            float bottomRightRadius = a.getFloat(R.styleable.ZTButton_bottomRightRadius, 0);
            if (topLeftRadius != 0 || bottomLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0) {
                setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
            }
            //边框宽度
            int borderWidth = (int) a.getDimension(R.styleable.ZTButton_borderWidth, 0);
            //边框颜色
            int borderColor = a.getColor(R.styleable.ZTButton_borderColor, bgColor);
            setBorder(borderWidth, borderColor);
        }
    }

    /**
     * 设置边框
     *
     * @param borderColor 边框颜色
     * @param borderWidth 边框宽度
     */
    public void setBorder(int borderWidth,int borderColor) {
        getGradientDrawable();
        shapeDrawable.setStroke(borderWidth, borderColor);
        setDrawable(shapeDrawable);
    }

    /**
     * 设置背景颜色
     *
     * @param bgColor
     */
    public void setBgColor(int bgColor) {
        getGradientDrawable();
        shapeDrawable.setColor(bgColor);
        setDrawable(shapeDrawable);
    }


    /**
     * 设置圆角
     *
     * @param radius 角度
     */
    private void setRadius(float radius) {
        getGradientDrawable();
        shapeDrawable.setCornerRadius(radius);
        setDrawable(shapeDrawable);
    }


    /**
     * 设置圆角
     *
     * @param topLeftRadius     左上
     * @param topRightRadius    右上
     * @param bottomLeftRadius  左下
     * @param bottomRightRadius 右下
     */
    public void setRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        getGradientDrawable();
        shapeDrawable.setCornerRadii(new float[]{
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius
        });
        setDrawable(shapeDrawable);
    }


    /**
     * 设置图形类型
     *
     * @param shape 形状
     */
    private void setShape(int shape) {
        getGradientDrawable();
        shapeDrawable.setShape(shape);
        setDrawable(shapeDrawable);
    }


    /**
     * 设置背景
     *
     * @param drawable 背景
     */
    private void setDrawable(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    /**
     * 获取需要设置到背景的图片
     */
    private void getGradientDrawable() {
        if (shapeDrawable == null) {
            shapeDrawable = new GradientDrawable();
        }
    }

    public int getBgColor() {
        return bgColor;
    }

    public float getRadius() {
        return radius;
    }

    public float getTopLeftRadius() {
        return topLeftRadius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public float getTopRightRadius() {
        return topRightRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public float getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public float getBottomRightRadius() {
        return bottomRightRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }
}
