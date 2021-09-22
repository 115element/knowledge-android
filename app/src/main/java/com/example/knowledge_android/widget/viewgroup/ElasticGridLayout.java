package com.example.knowledge_android.widget.viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.CollectionUtils;
import com.example.knowledge_android.knowledge.GUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import groovy.lang.GString;

public class ElasticGridLayout extends ViewGroup {


    public static boolean DEBUG = false;

    public static class GridLayoutParams extends LayoutParams {
        int row;
        int column;
        int rowSpan;
        int colSpan;

        GridLayoutParams() {
            super(MATCH_PARENT, MATCH_PARENT);
            row = column = rowSpan = colSpan = 1;
        }

        GridLayoutParams(int row, int column, int rowSpan, int colSpan) {
            super(MATCH_PARENT, MATCH_PARENT);
            this.row = row;
            this.column = column;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
        }

        GridLayoutParams(LayoutParams source) {
            super(source);
            row = column = rowSpan = colSpan = 1;
        }
    }

    /**
     * Row dimensions. E.g.,
     *   ['40dp',
     *    '40%',
     *    '40dp',
     *    '60%',
     *    '40dp']
     */
    List rows;

    /**
     * Column dimensions. E.g.,
     * ['50dp', '40.5%', '50dp', '59.5%', '50dp']
     * ['50dp', 40.5, '50dp', 59.5, '50dp']
     * ['50dp', 0.405, '50dp', 0.595, '50dp']
     * ['50dp', 10+20, '50dp', 20+30, '50dp']
     */
    List columns;

    /** Inner cell padding in pixel. */
    int cellPadding;

    boolean paintGridLines;

    boolean paintBorder; /**绘制边框*/

    int borderColor = Color.argb(200, 21, 130, 178);

    int bgColor;

    float radius;

    private Map<View, GridLayoutParams> children = new HashMap<>();
    private List<Integer> columnPos = new ArrayList<>();
    private List<Integer> rowPos = new ArrayList<>();

    public ElasticGridLayout(Context context) {
        super(context);
    }


    public void add(View view, int row, int column, int rowSpan, int colSpan) {
        GridLayoutParams layoutParams = new GridLayoutParams(row, column, rowSpan, colSpan);
        children.put(view,layoutParams);
        addView(view);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new GridLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new GridLayoutParams();
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof GridLayoutParams;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //measureChildren(widthMeasureSpec, heightMeasureSpec)

        int imposedWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int imposedHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        if (DEBUG) Log.i("####"," onMeasure: imposed width=$imposedWidth, height=$imposedHeight");
        if (DEBUG) Log.i("#### ","onMeasure: real width=$width, height=$height");

        int expectedWidth = Math.max(imposedWidth, getMinimumWidth());
        int expectedHeight = Math.max(imposedHeight, getMinimumHeight());
        if (DEBUG) Log.i("####"," onMeasure: doLayout width=$expectedWidth, height=$expectedHeight");

        if (expectedWidth > 0 && expectedHeight > 0)
            /*int childState =*/ doLayout(true, expectedWidth, expectedHeight);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Ref: https://stacktips.com/tutorials/android/how-to-create-custom-layout-in-android-by-extending-viewgroup-class
        ////若用下面这段代码setMeasuredDimension，DualLineItemList会不显示
        //setMeasuredDimension(resolveSizeAndState(imposedWidth, widthMeasureSpec, childState),
        //    resolveSizeAndState(imposedHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT))
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (DEBUG) {
            Log.i("####"," onLayout: left=$left, top=$top, right=$right, bottom=$bottom");
            Log.i("####"," onLayout: wdith=$width, height=$height, calculated => width=${right - left}, height=${bottom - top}");
        }
        doLayout(false, right - left, bottom - top);
    }

    /** Measure or layout all children. */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int doLayout(boolean measurePass, int width, int height) {
        if(CollectionUtils.isEmpty(rows) || CollectionUtils.isEmpty(columns)) {
            return 0;
        }

        double totalPerc = 0d;
        float parentWidthForPerc = width - (getPaddingLeft() + getPaddingRight());
        columnPos = new ArrayList<>();
        boolean byWeight = false;
        float totalWeight = 0f;
        for (Object col : columns) {
            if (col instanceof GString)
                col = col.toString();
            if (col instanceof String) {
                if (((String) col).endsWith("dp")) {
                    String col2 = (String) col;
                    String num = col2.replace("dp", "");
                    parentWidthForPerc -= GUtil.dip2px(Float.parseFloat(num));
                } else if (((String) col).endsWith("px")) {
                    String col2 = (String) col;
                    String num = col2.replace("px", "");
                    parentWidthForPerc -= GUtil.dip2px(Float.parseFloat(num));
                }
            } else if (col instanceof Number) {
                byWeight = true;
                totalWeight += ((Number) col).floatValue();
            }
        }

        float pos = getPaddingLeft();
        for (Object col : columns) {
            columnPos.add((int) pos);
            if (col instanceof GString)
                col = col.toString();
            if (col instanceof String) {
                if (((String) col).endsWith("dp")) {
                    String dp = ((String) col).replace("dp", "");
                    pos += GUtil.dip2px(Float.parseFloat(dp));
                } else if (((String) col).endsWith("px")) {
                    String dp = ((String) col).replace("px", "");
                    pos += GUtil.dip2px(Float.parseFloat(dp));
                } else if (((String) col).endsWith("%")) {
                    String dp = ((String) col).replace("%", "");
                    float perc = Float.parseFloat(dp);
                    pos += ((parentWidthForPerc * perc) / 100d);
                    totalPerc += perc;
                }
            } else if (byWeight) {
                Number col1 = (Number)col;
                float perc = (col1.floatValue() / totalWeight);
                float colW = (parentWidthForPerc * perc);
                pos += colW;
                totalPerc += perc;
            } else
                throw new IllegalArgumentException("Illegal column: ${col}, must be a number or ended with 'dp', 'px' or '%'");
        }
        if (totalPerc > 100f) {
            columnPos.add((int) pos);
        } else {
            columnPos.add(width - getPaddingLeft());// fix the inaccuracy
        }

        float parentHeightForPerc = height - (getPaddingTop() + getPaddingBottom());
        rowPos = new ArrayList<>();
        byWeight = false;
        totalWeight = 0f;
        for (Object row : rows) {
            if (row instanceof GString)
                row = row.toString();
            if (row instanceof String) {
                if (((String) row).endsWith("dp")) {
                    String dp = ((String) row).replace("dp", "");
                    parentHeightForPerc -= GUtil.dip2px((Float.parseFloat(dp)));
                } else if (((String) row).endsWith("px")) {
                    String dp = ((String) row).replace("px", "");
                    parentHeightForPerc -= Float.parseFloat(dp);
                }

            } else if (row instanceof Number) {
                byWeight = true;
                totalWeight += ((Number) row).floatValue();
            }
        }

        pos = getPaddingTop();
        totalPerc = 0f;
        for (Object row : rows) {
            rowPos.add((int) pos);
            if (row instanceof GString)
                row = row.toString();
            if (row instanceof String) {
                if (((String) row).endsWith("dp")) {
                    String dp = ((String) row).replace("dp", "");
                    float v = Float.parseFloat(dp);
                    pos += GUtil.dip2px(v);
                } else if (((String) row).endsWith("px")) {
                    String dp = ((String) row).replace("px", "");
                    float v = Float.parseFloat(dp);
                    pos += v;
                } else if (((String) row).endsWith("%")) {
                    String dp = ((String) row).replace("%", "");
                    float perc = Float.parseFloat(dp);
                    pos += ((parentHeightForPerc * perc) / 100d);
                    totalPerc += perc;
                }
            } else if (byWeight) {
                Number row1 = (Number) row;
                float perc = (row1.floatValue() / totalWeight);
                float rowH = (parentHeightForPerc * perc);
                pos += rowH;
                totalPerc += perc;
            } else
                throw new IllegalArgumentException("Illegal row: ${row}, must be a number or ended with 'dp', 'px' or '%'");
        }
        if (totalPerc > 100f) {
            rowPos.add((int) pos);
        } else {
            rows.add(height - getPaddingBottom());// fix the inaccuracy
        }
        /*
         *  Layout -
         *
         *  Layout is a two pass process: a measure pass and a layout pass. The measuring pass is
         *  implemented in measure(int, int) and is a top-down traversal of the view tree. Each view
         *  pushes dimension specifications down the tree during the recursion. At the end of the
         *  measure pass, every view has stored its measurements. The second pass happens in
         *  layout(int, int, int, int) and is also top-down. During this pass each parent is
         *  responsible for positioning all of its children using the sizes computed in the measure pass.
         *
         *  When a view's measure() method returns, its getMeasuredWidth() and getMeasuredHeight()
         *  values must be set, along with those for all of that view's descendants. A view's measured
         *  width and measured height values must respect the constraints imposed by the view's parents.
         *  This guarantees that at the end of the measure pass, all parents accept all of their children's
         *  measurements. A parent view may call measure() more than once on its children. For example,
         *  the parent may measure each child once with unspecified dimensions to find out how big they
         *  want to be, then call measure() on them again with actual numbers if the sum of all the
         *  children's unconstrained sizes is too big or too small.
         */
        AtomicInteger childIndex = new AtomicInteger(0);
        AtomicReference<String> curTag = new AtomicReference<>("");
        try {
            AtomicInteger childState = new AtomicInteger();

            children.forEach( (child,layoutParams) -> {
                if (child.getTag() == "LoginPanel") {
                    //println ''
                }
                curTag.set(child.getTag() == null ? "" : child.getTag().toString());
                childIndex.getAndIncrement();
                int l = columnPos.get(layoutParams.column - 1) + cellPadding;
                int t = rowPos.get(layoutParams.row - 1) + cellPadding;
                int r = columnPos.get(layoutParams.column - 1 + layoutParams.colSpan) - 1 - cellPadding;
                int b = rowPos.get(layoutParams.row - 1 + layoutParams.rowSpan) - 1 - cellPadding;
                int w = View.MeasureSpec.makeMeasureSpec(r - l, View.MeasureSpec.EXACTLY);
                int h = View.MeasureSpec.makeMeasureSpec(b - t, View.MeasureSpec.EXACTLY);
                if (measurePass) {
                    if (DEBUG) Log.i("####"," measure child: width=${r - l}, height=${b - t} of ${child.toString()}");
                    //measureChild(child, w, h) => this will make more padding, don't know why
                    child.measure(w, h);
                    childState.set(combineMeasuredStates(childState.get(), child.getMeasuredState()));
                } else {
                    if (DEBUG) Log.i("####"," layout child: width=${r - l}, height=${b - t} of ${child.toString()}");
                    child.layout(l, t, r, b);
                }
            }
            );
            return childState.get();
        } catch (Exception e) {
            //Log.e("tag=" + tag + "|curTag=" +  curTag + "|rows=" + rows.inspect() + "|cols" + columns.inspect());
            Log.e("TAG","onLayout failed", e);
            return 0;
        }
    }

    void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    //形状 图
    private GradientDrawable shapeDrawable;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (paintGridLines) {
            paintGridLines(canvas);
        }


        /** Alex 绘制边框**/
        if (paintBorder) {
            //1.获取布局控件宽高
            int width = getWidth();
            int height = getHeight();
            //2.创建画笔
            Paint mPaint = new Paint();
            //3.设置画笔的各个属性
            mPaint.setColor(borderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(7);
            mPaint.setAntiAlias(true);
//            if (radius > 0f) {
//                RectF mRectF = new RectF(0f, 0f, new Float(width), new Float(height));
//                canvas.drawRoundRect(mRectF, radius, radius, mPaint);
//            } else {
            //4.创建矩形框
            Rect mRect = new Rect(0, 0, width + 5, height);
            //5.绘制边框
            canvas.drawRect(mRect, mPaint);
//            }
            //6.最后必须调用父类的方法
            super.dispatchDraw(canvas);
        }
        if (this.radius > 0f) {
            getGradientDrawable();
            shapeDrawable.setColor(bgColor);
            shapeDrawable.setCornerRadius(radius);
            setDrawable(shapeDrawable);
        }
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

    /**
     * Paint grid lines for making debug easier.
     */
    private void paintGridLines(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(GUtil.getNormalTextSize() / 2f);

        int rn = 1;
        for (Integer r : rowPos) {
            canvas.drawLine (0f, r.floatValue(), getWidth(), r.floatValue(), paint);
            if (rn < rowPos.size()) {
                canvas.drawText(String.valueOf(rn), 3f, (r + rowPos.get(rn)) / 2f + 6f, paint);
                canvas.drawText(String.valueOf(rn), getWidth() - 8 , (r + rowPos.get(rn)) / 2 + 6, paint);
            }
            int cn = 1;
            for (Integer c : columnPos) {
                canvas.drawLine( c.floatValue(), 0f, c.floatValue(), getHeight(), paint);
                if (rn == 1 && cn < columnPos.size()) {
                    canvas.drawText( String.valueOf(cn), (c + columnPos.get(cn)) / 2f - 3f , 10f, paint);
                    canvas.drawText(String.valueOf(cn), (c + columnPos.get(cn)) / 2f - 3f, getHeight() - 6f, paint);
                }
                cn++;
            }
            rn++;
        }
    }
}
