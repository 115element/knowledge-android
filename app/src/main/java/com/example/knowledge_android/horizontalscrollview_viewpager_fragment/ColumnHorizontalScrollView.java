package com.example.knowledge_android.horizontalscrollview_viewpager_fragment;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;


public class ColumnHorizontalScrollView extends HorizontalScrollView {

    private View ll_content;
    private View ll_more;
    private View rl_column;
    private ImageView leftImage;
    private ImageView rightImage;
    private int mScreenWidth = 0;
    private Activity activity;

    public ColumnHorizontalScrollView(Context context) {
        super(context);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParam(Activity activity, int mScreenWidth, View ll_content, ImageView leftImage, ImageView rightImage, View ll_more, View rl_column) {
        this.activity = activity;
        this.mScreenWidth = mScreenWidth;
        this.ll_content = ll_content;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.ll_more = ll_more;
        this.rl_column = rl_column;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (!activity.isFinishing() && ll_content != null && leftImage != null && rightImage != null) {
            if (ll_content.getWidth() <= mScreenWidth) {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.GONE);
            } else {
                return;
            }
            if (l == 0) {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.VISIBLE);
                return;
            }
            if (ll_content.getWidth() - l + ll_more.getWidth() + rl_column.getLeft() == mScreenWidth) {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.VISIBLE);
                return;
            }
            leftImage.setVisibility(GONE);
            rightImage.setVisibility(VISIBLE);
        }
    }
}
