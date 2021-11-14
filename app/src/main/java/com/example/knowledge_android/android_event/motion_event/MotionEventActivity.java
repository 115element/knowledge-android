package com.example.knowledge_android.android_event.motion_event;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MotionEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //返回true表示事件被消费了

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int actionDown = MotionEvent.ACTION_DOWN;
        int actionMove = MotionEvent.ACTION_MOVE;
        int actionUp = MotionEvent.ACTION_UP;
        int actionCancel = MotionEvent.ACTION_CANCEL;
        int actionOutside = MotionEvent.ACTION_OUTSIDE;
        //....

        //down事件
        float rawX = ev.getRawX(); //事件在屏幕上的坐标
        float rawY = ev.getRawY(); //事件在屏幕上的坐标
        //move事件


        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
