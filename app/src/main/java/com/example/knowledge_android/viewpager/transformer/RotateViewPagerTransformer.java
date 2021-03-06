package com.example.knowledge_android.viewpager.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class RotateViewPagerTransformer implements ViewPager.PageTransformer {

    private static final float MAX_ROTATION=20.0f;

    @Override
    public void transformPage(View page, float position) {
        if(position<-1)
            rotate(page, -MAX_ROTATION);
        else if(position<=1)
            rotate(page, MAX_ROTATION*position);
        else
            rotate(page, MAX_ROTATION);
    }

    private void rotate(View view, float rotation) {
        view.setPivotX(view.getWidth()*0.5f);
        view.setPivotY(view.getHeight());
        view.setRotation(rotation);
    }

}
