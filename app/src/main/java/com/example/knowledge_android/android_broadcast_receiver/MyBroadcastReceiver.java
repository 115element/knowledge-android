package com.example.knowledge_android.android_broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


//广播接收器，应用退出到后台，它也在执行
//广播接收器 (静态注册)

public class MyBroadcastReceiver extends BroadcastReceiver {

    public MyBroadcastReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
