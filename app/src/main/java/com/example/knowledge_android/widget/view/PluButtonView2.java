package com.example.knowledge_android.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.knowledge_android.OneApplication;

import java.math.BigDecimal;

/**
 * @author alex
 * create on 2021-09-17
 * 实现一个自定义Button上半部分显示图片，下半部分显示文字描述，并且Button右上角绘制销售数量。
 */
public class PluButtonView2 extends AppCompatButton {

    /**
     * 关联商品。
     */
    //PluButton pluButton

    /**
     * 当前此商品的销售数量。
     */
    BigDecimal currentSaleQty = BigDecimal.ONE;

    public BigDecimal getCurrentSaleQty() {
        return currentSaleQty;
    }

    // Shared Paint
    private static TextPaint qtyPaint;

    // Shared Rect
    private static Rect rect = new Rect();

    // Shared Rect for text
    private static Rect textRect = new Rect();

    static {
        qtyPaint = new TextPaint();
        qtyPaint.setAntiAlias(true);

        qtyPaint.setTypeface(OneApplication.getInstance().getUserFont());
        qtyPaint.setTextSize(20);
    }

    PluButtonView2(Context context) {
        super(context);
    }

    PluButtonView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    PluButtonView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    private Bitmap bitmap;

    void setBitmap(int resourceId) {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        this.bitmap = resizeBitmap(210f, 100f, bitmap);
        this.bitmap = toRoundCorner(bitmap,20,false,false,true,true);
        invalidate();
    }

    /**
     * BitMap四个角自定义
     * 时间:2021-9-18下午15:42
     * 功能:设置圆角图片
     * 返回值:Bitmap
     * 参数：pixels--圆角半径，
     * 注意：true-表示直角，false--表示圆角
     * lt: left-top     左上
     * rt: right-top    右上
     * lb: left-below   左下
     * rb: right-below  右下
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels, boolean lt, boolean rt, boolean lb, boolean rb) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Paint paint1 = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        paint1.setColor(0xaaaaaaaa);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        if(lt){
            canvas.drawRect(0, 0, bitmap.getWidth()-pixels, bitmap.getHeight()-pixels, paint);
        }
        if(rt){
            canvas.drawRect(pixels, 0, bitmap.getWidth(), bitmap.getHeight()-pixels, paint);
        }
        if(lb){
            canvas.drawRect(0, pixels, bitmap.getWidth()-pixels, bitmap.getHeight(), paint);
        }
        if(rb){
            canvas.drawRect(pixels, pixels, bitmap.getWidth(), bitmap.getHeight(), paint);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    //获取圆角图片，方法1
    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = (int) 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        TextPaint paint = getPaint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 把图片变成圆角---方法2
     *
     * @param bitmap
     *            需要修改的图片
     * @param pixels
     *            圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



    //将bitmap转换成任意大小
    public Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return newBitmap;
    }

    /**
     * //BigDecimal比较大小   前提为a、b均不能为null
     * if(a.compareTo(b) == -1){
     *     System.out.println("a小于b");
     * }
     *
     * if(a.compareTo(b) == 0){
     *     System.out.println("a等于b");
     * }
     *
     * if(a.compareTo(b) == 1){
     *     System.out.println("a大于b");
     * }
     *
     * if(a.compareTo(b) > -1){
     *     System.out.println("a大于等于b");
     * }
     *
     * if(a.compareTo(b) < 1){
     *     System.out.println("a小于等于b");
     * }
     *
     *
     * @param canvas
     */


    //@Override
    protected void onDraw2(Canvas canvas) {
        //super.onDraw(canvas);

        // Draw quantity indicator
        String qtyString = "";
        if (getCurrentSaleQty().compareTo(BigDecimal.valueOf(99)) == 1) {
            qtyString = "99+";
        } else {
            qtyString = getCurrentSaleQty().stripTrailingZeros().toPlainString();
        }


        if (getCurrentSaleQty().compareTo(BigDecimal.ZERO) == 1) {
            getDrawingRect(rect);

            qtyPaint.getTextBounds(qtyString, 0, qtyString.length(), textRect);
            final float textHeight = textRect.height();
            final float padding = Math.min(4f, rect.width() / 9f);

            // Draw red circle
            qtyPaint.setColor((int) 0x8fe60337);
            qtyPaint.setStyle(Paint.Style.FILL);
            final float radius = determineRadiusOfCircle(qtyPaint, qtyString);
            final float centerX = (rect.right - padding - radius);
            final float centerY = (padding + radius);
            canvas.drawCircle(centerX, centerY, radius, qtyPaint);

            // Draw quantity text
            qtyPaint.setColor((int) 0xfff8f8f8);
            qtyPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(qtyString, centerX, (float) (centerY + textHeight / 2f - 1), qtyPaint);
        }

        canvas.drawBitmap(bitmap, 0, 0, null);
        // 坐标需要转换，因为默认情况下Button中的文字居中显示
        // 这里需要让文字在底部显示
        //canvas.translate(10, 10);//把当前画布的原点移到(10,10),后面的操作都以(10,10)作为参照点，默认原点为(0,0)
        canvas.translate(0, (this.getMeasuredHeight() / 2) - (int) this.getTextSize());
        //super.onDraw(canvas);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //getDrawingRect(rect)
        // TODO Auto-generated method stub

        // 图片顶部居中显示
        //int x = (this.getMeasuredWidth() - bitmap.getWidth())/2 as int;
        //int x = (this.getMeasuredWidth() - 100)/2 as int;
        //int y = 100;
        canvas.drawBitmap(bitmap, 0, 0, null);

        //canvas.save();//锁画布(为了保存之前的画布状态)

        // 坐标需要转换，因为默认情况下Button中的文字居中显示
        // 这里需要让文字在底部显示
        //canvas.translate(10, 10);//把当前画布的原点移到(10,10),后面的操作都以(10,10)作为参照点，默认原点为(0,0)
        //canvas.translate(0, (this.getMeasuredHeight()/2) - (int) this.getTextSize() as float);

        // Draw quantity indicator
        //canvas.restore();//把当前画布返回（调整）到上一个save()状态之前
        String qtyString = "";
        if (getCurrentSaleQty().compareTo(BigDecimal.valueOf(99)) == 1) {
            qtyString = "99+";
        } else {
            qtyString = getCurrentSaleQty().stripTrailingZeros().toPlainString();
        }

        if (getCurrentSaleQty().compareTo(BigDecimal.ZERO) == 1) {
            //getDrawingRect(rect)
            //getTextBounds 是将TextView 的文本放入一个矩形中， 测量TextView的高度和宽度，还有一下方法
            //利用此方法可以获取到包裹文字的最小矩形。
            //　　Rect textRect = new Rect();
            //　　mPaint.getTextBounds(textName, 0, textName.length(), textRect);
            //qtyPaint.getTextBounds(qtyString, 0, qtyString.length(), textRect)
            final float textHeight = textRect.height();
            final float padding = Math.min(4f, rect.width() / 9f);

            // Draw red circle
            qtyPaint.setColor((int) 0x8fe60337);
            qtyPaint.setStyle(Paint.Style.FILL);
            final float radius = determineRadiusOfCircle(qtyPaint, qtyString);
            //final float centerX = (rect.right - padding - radius) as float
            final float centerX = 200f;
            //final float centerY = (padding + radius) as float
            final float centerY = 30f;
            canvas.drawCircle(centerX, centerY, radius, qtyPaint);

            // Draw quantity text
            qtyPaint.setColor((int) 0xfff8f8f8);
            qtyPaint.setTextAlign(Paint.Align.CENTER);
            //canvas.drawText(qtyString, centerX, (float) (centerY + textHeight / 2f - 1), qtyPaint)
            canvas.drawText(qtyString, 200, 30, qtyPaint);
        }

        canvas.translate(0, (this.getMeasuredHeight() / 2) - (int) this.getTextSize());
        super.onDraw(canvas);
    }


    //@Override
    protected void onDraw1(Canvas canvas) {
        //log.info("Draw PluButtonView: ${pluButton.pluCode}")
        //super.onDraw(canvas);

        // Draw quantity indicator
        String qtyString = "";
        if (getCurrentSaleQty().compareTo(BigDecimal.valueOf(99)) == 1) {
            qtyString = "99+";
        } else {
            qtyString = getCurrentSaleQty().stripTrailingZeros().toPlainString();

        }

        if (getCurrentSaleQty().compareTo(BigDecimal.ZERO) == 1) {
            getDrawingRect(rect);

            qtyPaint.getTextBounds(qtyString, 0, qtyString.length(), textRect);
            final float textHeight = textRect.height();
            final float padding = Math.min(4f, rect.width() / 9f);

            // Draw red circle
            qtyPaint.setColor((int) 0x8fe60337);
            qtyPaint.setStyle(Paint.Style.FILL);
            final float radius = determineRadiusOfCircle(qtyPaint, qtyString);
            final float centerX = (rect.right - padding - radius);
            final float centerY = (padding + radius);
            canvas.drawCircle(centerX, centerY, radius, qtyPaint);

            // Draw quantity text
            qtyPaint.setColor((int) 0xfff8f8f8);
            qtyPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(qtyString, centerX, (float) (centerY + textHeight / 2f - 1), qtyPaint);
        }
    }

    private static float determineRadiusOfCircle(Paint paint, String qtyString) {
        int placeHolder = 0;
        for (int i = 0; i < qtyString.length(); i++) {
            // len 1 -> 8, len 2 -> 88, len 3 -> 888, ....
            placeHolder = placeHolder * 10 + 8;
        }
        String placeHolderString = String.valueOf(placeHolder);
        //利用此方法可以获取到包裹文字的最小矩形
        paint.getTextBounds(placeHolderString, 0, placeHolderString.length(), textRect);
        final float textWidth = textRect.width();
        final float textHeight = textRect.height();
        return Math.max(textWidth, textHeight);
    }
}
