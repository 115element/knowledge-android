package com.example.knowledge_android.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.knowledge_android.OneApplication;

import java.math.BigDecimal;

public class QtyChangeView extends SoundButtonView {

    public final static String CLICK_PLUS = "plus";
    public final static String CLICK_MINUS = "sub";
    public final static String CLICK_REMOVE = "remove";

    // 点击区域 (plus/sub/remove)
    String clickRegion = "";


    boolean showRemoveButton;

    /**
     * 当前此商品的销售数量。
     */
    BigDecimal currentSaleQty = BigDecimal.ZERO;

    // Shared Paint
    private static TextPaint screenPaint;

    // Shared Rect
    private static Rect rect = new Rect();

    // Shared Rect for text
    private static Rect textRect = new Rect();

    private int circleColor = Color.rgb(255, 144, 70);
    private int removeColor = Color.rgb(73, 73, 73);
    Path circlePathPlus = new Path();
    Path circlePathSub = new Path();
    Path circlePathRemove = new Path();

    Region circleRegionPlus = new Region();
    Region circleRegionSub = new Region();
    Region circleRegionRemove = new Region();
    Region globalRegion;

    static {
        screenPaint = new TextPaint();
        screenPaint.setAntiAlias(true);
        screenPaint.setTypeface(OneApplication.getInstance().getUserFont());
    }

    QtyChangeView(Context context) {
        super(context);
    }

    /**
     * 当前此商品的销售数量。
     */
    void setCurrentSaleQty(BigDecimal currentSaleQty) {
        if (!this.currentSaleQty.equals(currentSaleQty)) {
            this.currentSaleQty = currentSaleQty;
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        globalRegion = new Region(0, 0, w, h);
        circleRegionPlus.setPath(circlePathPlus, globalRegion);
        circleRegionSub.setPath(circlePathSub, globalRegion);
        circleRegionRemove.setPath(circlePathRemove, globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(rect);

        int totalWidth = 0;
        int separateWidth = 5;
        final float flagRadius = determineRadiusOfCircle(screenPaint, "x", rect);
        int flagWidth = (int) (2 * flagRadius + separateWidth);
        if (showRemoveButton) {
            String removeFlag = "x";
            screenPaint.getTextBounds(removeFlag, 0, removeFlag.length(), textRect);
            screenPaint.setColor(removeColor);
            screenPaint.setStyle(Paint.Style.FILL);
            final float flagTextHeight = textRect.height();
            final float flagCenterX = (rect.width() - flagWidth);
            final float flagCenterY = (rect.height() - flagRadius);
            circlePathRemove.addCircle(flagCenterX, flagCenterY, flagRadius, Path.Direction.CW);
            canvas.drawPath(circlePathRemove, screenPaint);
            totalWidth = flagWidth + 4 * separateWidth;

            screenPaint.setColor(Color.WHITE);
            screenPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(removeFlag, flagCenterX, (float) (flagCenterY + flagTextHeight / 2f - 1), screenPaint);
            circleRegionRemove.setPath(circlePathRemove, globalRegion);
        }

        String plusFlag = "+";
        screenPaint.getTextBounds(plusFlag, 0, plusFlag.length(), textRect);
        screenPaint.setColor(circleColor);
        screenPaint.setStyle(Paint.Style.FILL);
        final float flagTextHeight = textRect.height();
        final float flagCenterX = (rect.width() - flagWidth - totalWidth);
        final float flagCenterY = (rect.height() - flagRadius);
        //2 * separateWidth -
        circlePathPlus.addCircle(flagCenterX, flagCenterY, flagRadius, Path.Direction.CW);
        canvas.drawPath(circlePathPlus, screenPaint);
        totalWidth += flagWidth;

        screenPaint.setColor(Color.WHITE);
        screenPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(plusFlag, flagCenterX, (float) (flagCenterY + flagTextHeight / 2f - 1), screenPaint);
        circleRegionPlus.setPath(circlePathPlus, globalRegion);

        if (currentSaleQty.compareTo(BigDecimal.ZERO) == 1) {
            // 数量
            final String qtyString = currentSaleQty.stripTrailingZeros().toPlainString();
            screenPaint.getTextBounds(qtyString, 0, qtyString.length(), textRect);
            final float qtyTextHeight = textRect.height();
            screenPaint.setColor(Color.BLACK);
            int qtyWidth = textRect.width() + 5 * 10;
            final float qtyCenterX = (rect.width() - qtyWidth - totalWidth);
            final float qtyCenterY = (rect.height() - (qtyTextHeight / 2f - 1));
            screenPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(qtyString, qtyCenterX, qtyCenterY, screenPaint);
            totalWidth += qtyWidth;
        }

        if ((showRemoveButton && currentSaleQty.compareTo(BigDecimal.ZERO) == 1) ||
                (!showRemoveButton && currentSaleQty.compareTo(BigDecimal.ZERO) == 1)) {
            // 减
            String subFlag = "-";
            screenPaint.getTextBounds(subFlag, 0, subFlag.length(), textRect);
            screenPaint.setColor(circleColor);
            screenPaint.setStyle(Paint.Style.FILL);
            final float subFlagCenterX = (rect.width() - flagWidth - totalWidth);
            circlePathSub.addCircle(subFlagCenterX, flagCenterY, flagRadius, Path.Direction.CW);
            canvas.drawPath(circlePathSub, screenPaint);

            screenPaint.setColor(Color.WHITE);
            screenPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(subFlag, subFlagCenterX, (float) (flagCenterY + flagTextHeight / 2f - 1), screenPaint);
            circleRegionSub.setPath(circlePathSub, globalRegion);
        }
    }

    private static float determineRadiusOfCircle(Paint paint, String qtyString, Rect rect) {
        int placeHolder = 0;
        for (int i = 0; i < qtyString.length(); i++) {
            // len 1 -> 8, len 2 -> 88, len 3 -> 888, ....
            placeHolder = placeHolder * 10 + 8;
        }
        String placeHolderString = String.valueOf(placeHolder);
        paint.getTextBounds(placeHolderString, 0, placeHolderString.length(), textRect);
        final float textWidth = textRect.width();
        final float textHeight = textRect.height();
        //println 'determineRadiusOfCircle ' + textWidth + ' | ' + textHeight + ' | ' + rect.height()
        return Math.min(Math.max(textWidth, textHeight), rect.height() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            // 点击区域判断
            clickRegion = "";
            //println 'circleRegion1=' + circleRegionPlus.getBounds()
            //println 'circleRegion2=' + circleRegionSub.getBounds()
            if (circleRegionPlus.contains(x, y)) {
                clickRegion = CLICK_PLUS;
                Toast.makeText(this.getContext(), "圆1被点击", Toast.LENGTH_SHORT).show();
            }
            if (circleRegionSub.contains(x, y)) {
                clickRegion = CLICK_MINUS;
                Toast.makeText(this.getContext(), "圆2被点击", Toast.LENGTH_SHORT).show();
            }
            if (circleRegionRemove.contains(x, y)) {
                clickRegion = CLICK_REMOVE;
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (!clickRegion.equals("")) {
                playClickingSound(); // make clicking sound
            }
        }
        return false;
    }
}
