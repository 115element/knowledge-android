package com.example.knowledge_android.widget.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class MarqueeText extends View {

    private TextPaint mPaint;
    private String mText = "";
    private int mSpeed;
    private float mCurLeft;
    private float mLastLeft;
    private ValueAnimator mAnimator;
    private float mTextWidth;
    private float mTextHeight;
    private boolean mIsRunning;
    private TimeInterpolator mInterpolator;

    MarqueeText(Context context) {
        super(context);
        init();
    }

    public MarqueeText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT);
        mSpeed = 120;
    }

    public void setTextColor(int textColor) {
        mPaint.setColor(textColor);
    }

    public void setTextSize(int textSize) {
        mPaint.setTextSize(textSize);
    }

    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }

    public void setShadowLayer(float radius, float dx, float dy, int shadowColor) {
        mPaint.setShadowLayer(radius, dx, dy, shadowColor);
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
        restart();
    }

    private void ensureAnimator() {
        mAnimator = ValueAnimator.ofFloat(0, 1);
        if (mInterpolator != null) {
            mInterpolator = new LinearInterpolator();
        }
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float factor = valueAnimator.getAnimatedFraction();
                float width = getWidth();
                mCurLeft = width - (width + mTextWidth) * factor;
                setTranslationX(mCurLeft);
            }
        });
    }

    public void setText(String text) {
        //log.info("DS200 setText: $text")
        if (mText.equals(text)) {
            return;
        }
        mText = text;

        restart();
        invalidate();
    }

    void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mText)) {
            float y = getHeight() - (getHeight() - mTextHeight) / 2f;
            canvas.drawText(mText, 0, y, mPaint);
            //log.info("DS200 Draw $mText on (0, $y)")
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // only make the view wrap txt
        int height = (int) (mPaint.descent() - mPaint.ascent()) + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), height);
    }

    private void restart() {
        if (mIsRunning) {
            stop();
        }

        if (!TextUtils.isEmpty(mText)) {
            start();
        }
    }

    private void start() {
        if (mIsRunning) {
            return;
        }

        Rect textBounds = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), textBounds);
        mTextWidth = textBounds.width();
        mTextHeight = textBounds.height();

        if (mTextWidth <= getWidth()) // 宽度没超过显示范围，则不滚动显示
            return;

        if (mAnimator == null) {
            ensureAnimator();
        }
        mAnimator.setDuration((long) (mTextWidth * 1000 / mSpeed));
        mAnimator.start();
        mIsRunning = true;

        //log.info("Start marquee: $mText, mTextWidth=$mTextWidth, mTextHeight=$mTextHeight")
    }

    private void stop() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mLastLeft = mCurLeft = 0;
            setTranslationX(0);
            mAnimator = null;
        }
        mIsRunning = false;
    }
}
