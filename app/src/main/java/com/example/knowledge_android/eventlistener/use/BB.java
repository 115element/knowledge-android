package com.example.knowledge_android.eventlistener.use;

import android.util.Log;

import com.example.knowledge_android.eventlistener.MyEvent;
import com.example.knowledge_android.eventlistener.MyListener;

public class BB implements MyListener {
    @Override
    public void transactionChanged(MyEvent var1) {
        String msg = var1.msg;
        Log.i("BB","我监听到了事件哦:"+msg);
    }
}
