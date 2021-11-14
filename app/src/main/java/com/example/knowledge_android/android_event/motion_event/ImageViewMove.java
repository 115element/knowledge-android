package com.example.knowledge_android.android_event.motion_event;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageViewMove extends AppCompatActivity implements View.OnTouchListener {

    private LinearLayout parent;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageView.setOnTouchListener(this::onTouch);
        parent = (LinearLayout) imageView.getParent();
    }


    private int lastX;
    private int lastY;

    private int maxRight;
    private int maxBottom;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //得到事件的坐标
        int eventX = (int) motionEvent.getRawX();
        int eventY = (int) motionEvent.getRawY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //得到父视图的right/bottom
                maxRight = parent.getRight();
                maxBottom = parent.getBottom();

                //第一次记录 lastX/lastY
                lastX = eventX;
                lastY = eventY;
                break;

            case MotionEvent.ACTION_MOVE:
                //计算偏移
                int dx = eventX - lastX;
                int dy = eventY - lastY;
                //根据事件的偏移移动视图
                int left = imageView.getLeft() + dx;
                int top = imageView.getTop() + dy;
                int right = imageView.getRight() + dx;
                int bottom = imageView.getBottom() + dy;


                //限制left >= 0
                if (left < 0) {
                    right += -left;
                    left = 0;
                }
                //限制top
                if (top < 0) {
                    bottom += -top;
                    top = 0;
                }
                //限制right <= maxRight;
                if (right > maxRight) {
                    left -= right - maxRight;
                    right = maxRight;
                }
                //限制bottom <= maxBottom
                if (bottom > maxBottom) {
                    top -= bottom - maxBottom;
                    bottom = maxBottom;
                }


                imageView.layout(left, top, right, bottom);
                //再次记录 lastX/lastY
                lastX = eventX;
                lastY = eventY;
                break;

            default:
                break;
        }

        return true;
    }
}
