package com.example.knowledge_android.android_event.key_enent;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class KeyEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //用于分发Key事件
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        int action = event.getAction();
        int keyCode1 = event.getKeyCode();

        //startTracking() : 追踪事件，用于长按监听
        event.startTracking();
        return true;

        //return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        int action = event.getAction();
        int keyCode1 = event.getKeyCode();

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        int action = event.getAction();
        int keyCode1 = event.getKeyCode();

        return super.onKeyLongPress(keyCode, event);
    }
}
