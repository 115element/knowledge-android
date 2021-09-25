package com.example.knowledge_android.eventlistener.use;

import android.util.Log;

import com.example.knowledge_android.eventlistener.MyEvent;
import com.example.knowledge_android.eventlistener.MyListener;

public class AA implements MyListener {

    @Override
    public void transactionChanged(MyEvent myEvent) {
        String msg = myEvent.msg;
        Log.i("AA","我监听到了事件哦:"+msg);
    }
}
