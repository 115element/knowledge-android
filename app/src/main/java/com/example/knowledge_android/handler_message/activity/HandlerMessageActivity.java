package com.example.knowledge_android.handler_message.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HandlerMessageActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) { //该方法在主线程执行
                if (msg.what == 1) {
                    Object obj = msg.obj;
                }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Message message = Message.obtain();
        message.what = 1; //标识
        message.obj = new Object(); //数据
        handler.sendMessage(message); //发送消息
    }
}
