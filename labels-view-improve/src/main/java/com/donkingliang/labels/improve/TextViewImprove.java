package com.donkingliang.labels.improve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.DisplayUtil;
import q.rorbin.badgeview.MathUtil;

/**
 * @author Alex
 * @since 2021-10-17 22:27:00
 * 自定义TextView，具有Badge功能
 */
public class TextViewImprove extends androidx.appcompat.widget.AppCompatTextView {

    //右上提示信息(不完美)，只能圆形
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
        initBadge();
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


        //////////////////////////以下使用自定义Badge///////////////////////////////////////
        if (mBadgeText != null) {
            initPaints();
            float badgeRadius = getBadgeCircleRadius();
            float startCircleRadius = mDefalutRadius * (1 - MathUtil.getPointDistance
                    (mRowBadgeCenter, mDragCenter) / mFinalDragDistance);
            if (mDraggable && mDragging) {
                mDragQuadrant = MathUtil.getQuadrant(mDragCenter, mRowBadgeCenter);
                showShadowImpl(mShowShadow);
                if (mDragOutOfRange = startCircleRadius < DisplayUtil.dp2px(getContext(), 1.5f)) {
                    //updataListener(Badge.OnDragStateChangedListener.STATE_DRAGGING_OUT_OF_RANGE);
                    drawBadge(canvas, mDragCenter, badgeRadius);
                } else {
                    //updataListener(Badge.OnDragStateChangedListener.STATE_DRAGGING);
                    drawDragging(canvas, startCircleRadius, badgeRadius);
                    drawBadge(canvas, mDragCenter, badgeRadius);
                }
            } else {
                findBadgeCenter();
                drawBadge(canvas, mBadgeCenter, badgeRadius);
            }
        }



        /////////////////////////////以下是绘制右上提示/////////////////////////////////////////////////
//        if (!TextUtils.isEmpty(topLeftWarn)) {
//            int centerX = width - marginLeftRight - 20;
//            int centerY = marginTopBottom + 5;
//
//            ////画红色圆
//            qtyPaint.setColor(Color.RED);
//            canvas.drawCircle(centerX, centerY, 18, qtyPaint);
//            ////写白色字
//            qtyPaint.setColor(Color.WHITE);
//            qtyPaint.setTextAlign(Paint.Align.CENTER);
//            canvas.drawText(topLeftWarn, centerX, centerY, qtyPaint);
//        }


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


    private void findBadgeCenter() {
        float rectWidth = mBadgeTextRect.height() > mBadgeTextRect.width() ?
                mBadgeTextRect.height() : mBadgeTextRect.width();
        switch (mBadgeGravity) {
            case Gravity.START | Gravity.TOP:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.START | Gravity.BOTTOM:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.END | Gravity.TOP:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.END | Gravity.BOTTOM:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.CENTER:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case Gravity.CENTER | Gravity.TOP:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f;
                break;
            case Gravity.CENTER | Gravity.BOTTOM:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mBadgePadding + mBadgeTextRect.height() / 2f);
                break;
            case Gravity.CENTER | Gravity.START:
                mBadgeCenter.x = mGravityOffsetX + mBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case Gravity.CENTER | Gravity.END:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight / 2f;
                break;
        }
        initRowBadgeCenter();
    }

    private void initRowBadgeCenter() {
        int[] screenPoint = new int[2];
        getLocationOnScreen(screenPoint);
        mRowBadgeCenter.x = mBadgeCenter.x + screenPoint[0];
        mRowBadgeCenter.y = mBadgeCenter.y + screenPoint[1];
    }


    private void drawDragging(Canvas canvas, float startRadius, float badgeRadius) {
        float dy = mDragCenter.y - mRowBadgeCenter.y;
        float dx = mDragCenter.x - mRowBadgeCenter.x;
        mInnertangentPoints.clear();
        if (dx != 0) {
            double k1 = dy / dx;
            double k2 = -1 / k1;
            MathUtil.getInnertangentPoints(mDragCenter, badgeRadius, k2, mInnertangentPoints);
            MathUtil.getInnertangentPoints(mRowBadgeCenter, startRadius, k2, mInnertangentPoints);
        } else {
            MathUtil.getInnertangentPoints(mDragCenter, badgeRadius, 0d, mInnertangentPoints);
            MathUtil.getInnertangentPoints(mRowBadgeCenter, startRadius, 0d, mInnertangentPoints);
        }
        mDragPath.reset();
        mDragPath.addCircle(mRowBadgeCenter.x, mRowBadgeCenter.y, startRadius,
                mDragQuadrant == 1 || mDragQuadrant == 2 ? Path.Direction.CCW : Path.Direction.CW);
        mControlPoint.x = (mRowBadgeCenter.x + mDragCenter.x) / 2.0f;
        mControlPoint.y = (mRowBadgeCenter.y + mDragCenter.y) / 2.0f;
        mDragPath.moveTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
        mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(0).x, mInnertangentPoints.get(0).y);
        mDragPath.lineTo(mInnertangentPoints.get(1).x, mInnertangentPoints.get(1).y);
        mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(3).x, mInnertangentPoints.get(3).y);
        mDragPath.lineTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
        mDragPath.close();
        canvas.drawPath(mDragPath, mBadgeBackgroundPaint);

        //draw dragging border
        if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
            mDragPath.reset();
            mDragPath.moveTo(mInnertangentPoints.get(2).x, mInnertangentPoints.get(2).y);
            mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(0).x, mInnertangentPoints.get(0).y);
            mDragPath.moveTo(mInnertangentPoints.get(1).x, mInnertangentPoints.get(1).y);
            mDragPath.quadTo(mControlPoint.x, mControlPoint.y, mInnertangentPoints.get(3).x, mInnertangentPoints.get(3).y);
            float startY;
            float startX;
            if (mDragQuadrant == 1 || mDragQuadrant == 2) {
                startX = mInnertangentPoints.get(2).x - mRowBadgeCenter.x;
                startY = mRowBadgeCenter.y - mInnertangentPoints.get(2).y;
            } else {
                startX = mInnertangentPoints.get(3).x - mRowBadgeCenter.x;
                startY = mRowBadgeCenter.y - mInnertangentPoints.get(3).y;
            }
            float startAngle = 360 - (float) MathUtil.radianToAngle(MathUtil.getTanRadian(Math.atan(startY / startX),
                    mDragQuadrant - 1 == 0 ? 4 : mDragQuadrant - 1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDragPath.addArc(mRowBadgeCenter.x - startRadius, mRowBadgeCenter.y - startRadius,
                        mRowBadgeCenter.x + startRadius, mRowBadgeCenter.y + startRadius, startAngle,
                        180);
            } else {
                mDragPath.addArc(new RectF(mRowBadgeCenter.x - startRadius, mRowBadgeCenter.y - startRadius,
                        mRowBadgeCenter.x + startRadius, mRowBadgeCenter.y + startRadius), startAngle, 180);
            }
            canvas.drawPath(mDragPath, mBadgeBackgroundBorderPaint);
        }
    }

    private void initPaints() {
        showShadowImpl(mShowShadow);
        mBadgeBackgroundPaint.setColor(mColorBackground);
        mBadgeBackgroundBorderPaint.setColor(mColorBackgroundBorder);
        mBadgeBackgroundBorderPaint.setStrokeWidth(mBackgroundBorderWidth);
        mBadgeTextPaint.setColor(mColorBadgeText);
        mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void showShadowImpl(boolean showShadow) {
        int x = DisplayUtil.dp2px(getContext(), 1);
        int y = DisplayUtil.dp2px(getContext(), 1.5f);
        switch (mDragQuadrant) {
            case 1:
                x = DisplayUtil.dp2px(getContext(), 1);
                y = DisplayUtil.dp2px(getContext(), -1.5f);
                break;
            case 2:
                x = DisplayUtil.dp2px(getContext(), -1);
                y = DisplayUtil.dp2px(getContext(), -1.5f);
                break;
            case 3:
                x = DisplayUtil.dp2px(getContext(), -1);
                y = DisplayUtil.dp2px(getContext(), 1.5f);
                break;
            case 4:
                x = DisplayUtil.dp2px(getContext(), 1);
                y = DisplayUtil.dp2px(getContext(), 1.5f);
                break;
        }
        mBadgeBackgroundPaint.setShadowLayer(showShadow ? DisplayUtil.dp2px(getContext(), 2f)
                : 0, x, y, 0x33000000);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void setBadgeText(String badgeText) {
        mBadgeText = badgeText;
        mBadgeNumber = 1;
        measureText();
        invalidate();
    }

    protected int mColorBackground;
    protected int mColorBackgroundBorder;
    protected int mColorBadgeText;
    protected Drawable mDrawableBackground;
    protected Bitmap mBitmapClip;
    protected boolean mDrawableBackgroundClip;
    protected float mBackgroundBorderWidth;
    protected float mBadgeTextSize;
    protected float mBadgePadding;
    protected int mBadgeNumber;
    protected String mBadgeText;
    protected boolean mDraggable;
    protected boolean mDragging;
    protected boolean mExact;
    protected boolean mShowShadow;
    protected int mBadgeGravity;
    protected float mGravityOffsetX;
    protected float mGravityOffsetY;

    protected float mDefalutRadius;
    protected float mFinalDragDistance;
    protected int mDragQuadrant;
    protected boolean mDragOutOfRange;

    protected RectF mBadgeTextRect;
    protected RectF mBadgeBackgroundRect;
    protected Path mDragPath;

    protected Paint.FontMetrics mBadgeTextFontMetrics;

    protected PointF mBadgeCenter;
    protected PointF mDragCenter;
    protected PointF mRowBadgeCenter;
    protected PointF mControlPoint;

    protected List<PointF> mInnertangentPoints;

    protected View mTargetView;

    protected int mWidth;
    protected int mHeight;

    protected TextPaint mBadgeTextPaint;
    protected Paint mBadgeBackgroundPaint;
    protected Paint mBadgeBackgroundBorderPaint;
    private void initBadge() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBadgeTextRect = new RectF();
        mBadgeBackgroundRect = new RectF();
        mDragPath = new Path();
        mBadgeCenter = new PointF();
        mDragCenter = new PointF();
        mRowBadgeCenter = new PointF();
        mControlPoint = new PointF();
        mInnertangentPoints = new ArrayList<>();
        mBadgeTextPaint = new TextPaint();
        mBadgeTextPaint.setAntiAlias(true);
        mBadgeTextPaint.setSubpixelText(true);
        mBadgeTextPaint.setFakeBoldText(true);
        mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mBadgeBackgroundPaint = new Paint();
        mBadgeBackgroundPaint.setAntiAlias(true);
        mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
        mBadgeBackgroundBorderPaint = new Paint();
        mBadgeBackgroundBorderPaint.setAntiAlias(true);
        mBadgeBackgroundBorderPaint.setStyle(Paint.Style.STROKE);
        //mColorBackground = 0xFFE84E40; //source code color
        mColorBackground = 0xFFC25348;
        mColorBadgeText = 0xFFFFFFFF;
        mBadgeTextSize = DisplayUtil.dp2px(getContext(), 11);
        mBadgePadding = DisplayUtil.dp2px(getContext(), 5);
        mBadgeNumber = 0;
        mBadgeGravity = Gravity.END | Gravity.TOP;
        mGravityOffsetX = DisplayUtil.dp2px(getContext(), 1);
        mGravityOffsetY = DisplayUtil.dp2px(getContext(), 1);
        mFinalDragDistance = DisplayUtil.dp2px(getContext(), 90);
        mShowShadow = true;
        mDrawableBackgroundClip = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslationZ(1000);
        }
    }

    //badge animation use
    protected Bitmap createBadgeBitmap() {
        Bitmap bitmap = Bitmap.createBitmap((int) mBadgeBackgroundRect.width() + DisplayUtil.dp2px(getContext(), 3),
                (int) mBadgeBackgroundRect.height() + DisplayUtil.dp2px(getContext(), 3), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawBadge(canvas, new PointF(canvas.getWidth() / 2f, canvas.getHeight() / 2f), getBadgeCircleRadius());
        return bitmap;
    }
    private float getBadgeCircleRadius() {
        if (mBadgeText.isEmpty()) {
            return mBadgePadding;
        } else if (mBadgeText.length() == 1) {
            return mBadgeTextRect.height() > mBadgeTextRect.width() ?
                    mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f :
                    mBadgeTextRect.width() / 2f + mBadgePadding * 0.5f;
        } else {
            return mBadgeBackgroundRect.height() / 2f;
        }
    }

    private void measureText() {
        mBadgeTextRect.left = 0;
        mBadgeTextRect.top = 0;
        if (TextUtils.isEmpty(mBadgeText)) {
            mBadgeTextRect.right = 0;
            mBadgeTextRect.bottom = 0;
        } else {
            mBadgeTextPaint.setTextSize(mBadgeTextSize);
            mBadgeTextRect.right = mBadgeTextPaint.measureText(mBadgeText);
            mBadgeTextFontMetrics = mBadgeTextPaint.getFontMetrics();
            mBadgeTextRect.bottom = mBadgeTextFontMetrics.descent - mBadgeTextFontMetrics.ascent;
        }
        createClipLayer();
    }
    private void drawBadge(Canvas canvas, PointF center, float radius) {
        if (center.x == -1000 && center.y == -1000) {
            return;
        }
        if (mBadgeText.isEmpty() || mBadgeText.length() == 1) {
            mBadgeBackgroundRect.left = center.x - (int) radius;
            mBadgeBackgroundRect.top = center.y - (int) radius;
            mBadgeBackgroundRect.right = center.x + (int) radius;
            mBadgeBackgroundRect.bottom = center.y + (int) radius;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundPaint);
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundBorderPaint);
                }
            }
        } else {
            mBadgeBackgroundRect.left = center.x - (mBadgeTextRect.width() / 2f + mBadgePadding);
            mBadgeBackgroundRect.top = center.y - (mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f);
            mBadgeBackgroundRect.right = center.x + (mBadgeTextRect.width() / 2f + mBadgePadding);
            mBadgeBackgroundRect.bottom = center.y + (mBadgeTextRect.height() / 2f + mBadgePadding * 0.5f);
            radius = mBadgeBackgroundRect.height() / 2f;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundPaint);
                if (mColorBackgroundBorder != 0 && mBackgroundBorderWidth > 0) {
                    canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundBorderPaint);
                }
            }
        }
        if (!mBadgeText.isEmpty()) {
            canvas.drawText(mBadgeText, center.x,
                    (mBadgeBackgroundRect.bottom + mBadgeBackgroundRect.top
                            - mBadgeTextFontMetrics.bottom - mBadgeTextFontMetrics.top) / 2f,
                    mBadgeTextPaint);
        }
    }

    private void createClipLayer() {
        if (mBadgeText == null) {
            return;
        }
        if (!mDrawableBackgroundClip) {
            return;
        }
        if (mBitmapClip != null && !mBitmapClip.isRecycled()) {
            mBitmapClip.recycle();
        }
        float radius = getBadgeCircleRadius();
        if (mBadgeText.isEmpty() || mBadgeText.length() == 1) {
            mBitmapClip = Bitmap.createBitmap((int) radius * 2, (int) radius * 2,
                    Bitmap.Config.ARGB_4444);
            Canvas srcCanvas = new Canvas(mBitmapClip);
            srcCanvas.drawCircle(srcCanvas.getWidth() / 2f, srcCanvas.getHeight() / 2f,
                    srcCanvas.getWidth() / 2f, mBadgeBackgroundPaint);
        } else {
            mBitmapClip = Bitmap.createBitmap((int) (mBadgeTextRect.width() + mBadgePadding * 2),
                    (int) (mBadgeTextRect.height() + mBadgePadding), Bitmap.Config.ARGB_4444);
            Canvas srcCanvas = new Canvas(mBitmapClip);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                srcCanvas.drawRoundRect(0, 0, srcCanvas.getWidth(), srcCanvas.getHeight(), srcCanvas.getHeight() / 2f,
                        srcCanvas.getHeight() / 2f, mBadgeBackgroundPaint);
            } else {
                srcCanvas.drawRoundRect(new RectF(0, 0, srcCanvas.getWidth(), srcCanvas.getHeight()),
                        srcCanvas.getHeight() / 2f, srcCanvas.getHeight() / 2f, mBadgeBackgroundPaint);
            }
        }
    }

    private void drawBadgeBackground(Canvas canvas) {
        mBadgeBackgroundPaint.setShadowLayer(0, 0, 0, 0);
        int left = (int) mBadgeBackgroundRect.left;
        int top = (int) mBadgeBackgroundRect.top;
        int right = (int) mBadgeBackgroundRect.right;
        int bottom = (int) mBadgeBackgroundRect.bottom;
        if (mDrawableBackgroundClip) {
            right = left + mBitmapClip.getWidth();
            bottom = top + mBitmapClip.getHeight();
            canvas.saveLayer(left, top, right, bottom, null, Canvas.ALL_SAVE_FLAG);
        }
        mDrawableBackground.setBounds(left, top, right, bottom);
        mDrawableBackground.draw(canvas);
        if (mDrawableBackgroundClip) {
            mBadgeBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(mBitmapClip, left, top, mBadgeBackgroundPaint);
            canvas.restore();
            mBadgeBackgroundPaint.setXfermode(null);
            if (mBadgeText.isEmpty() || mBadgeText.length() == 1) {
                canvas.drawCircle(mBadgeBackgroundRect.centerX(), mBadgeBackgroundRect.centerY(),
                        mBadgeBackgroundRect.width() / 2f, mBadgeBackgroundBorderPaint);
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect,
                        mBadgeBackgroundRect.height() / 2, mBadgeBackgroundRect.height() / 2,
                        mBadgeBackgroundBorderPaint);
            }
        } else {
            canvas.drawRect(mBadgeBackgroundRect, mBadgeBackgroundBorderPaint);
        }
    }

}
