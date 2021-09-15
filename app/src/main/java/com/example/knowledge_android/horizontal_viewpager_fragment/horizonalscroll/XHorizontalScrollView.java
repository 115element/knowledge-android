package com.example.knowledge_android.horizontal_viewpager_fragment.horizonalscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class XHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {

    //屏幕的宽度
    private int mScreenWidth;

    public XHorizontalScrollView(Context context) {
        super(context);
    }

    public XHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得屏幕宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("TTT","滚动事件触发");
    }

    @Override
    public void onClick(View view) {
        Log.i("XHorizontalScrollView","点击");
    }


    //数据适配器
    private XHorizontalScrollViewAdapter mAdapter;
    // HorizontalScrollView中的LinearLayout
    private LinearLayout mContainer;
    //子元素宽度
    private int mChildWidth;
    //子元素高度
    private int mChildHeight;
    //当前最后一张图片的index
    private int mCurrentIndex;
    //当前第一张图片的下标
    private int mFirstIndex;
    //每个屏幕最多显示的个数
    private int mCountOneScreen;
    //保存View与位置键值对
    private Map<View, Integer> mViewPos = new HashMap<View, Integer>();

    public void initDatas(XHorizontalScrollViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) getChildAt(0);
        // 获得适配器中第一个View
        final View view = mAdapter.getView(0, null, mContainer);
        mContainer.addView(view);

        // 强制计算当前View的宽和高
        if (mChildWidth == 0 && mChildHeight == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            Log.e("ss", view.getMeasuredWidth() + "," + view.getMeasuredHeight());
            mChildHeight = view.getMeasuredHeight();
            // 计算每次加载多少个View
            mCountOneScreen = mScreenWidth / mChildWidth + 2;

            Log.e("TAG", "mCountOneScreen = " + mCountOneScreen
                    + " ,mChildWidth = " + mChildWidth);

        }
        //判断加载的图片是否小于一个屏幕加载数
        if (mAdapter.getCount() < mCountOneScreen) {
            mCountOneScreen = mAdapter.getCount();
        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(mCountOneScreen);
    }

    public void initFirstScreenChildren(int mCountOneScreen) {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();

        for (int i = 0; i < mCountOneScreen; i++) {
            View view = mAdapter.getView(i, null, mContainer);
//            view.setOnClickListener(this);
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i("TAG","点");
//                }
//            });

            mContainer.addView(view);
            mViewPos.put(view, i);
            mCurrentIndex = i;
        }
    }
}
