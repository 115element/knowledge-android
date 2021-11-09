package com.example.knowledge_android.ui_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 安卓如何在非UI线程，更新UI
 */

public class UiActivity extends AppCompatActivity {


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 16:
                    String result = msg.getData().getString("result");//接受msg传递过来的参数
                    //这里操作更新ui
                    System.out.println("Handler::::"+"这里操作更新ui");
                    break;
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //①如何在非UI线程，通知更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });


        //①如何在非UI线程，通知更新UI
        Message message = new Message();
        message.what = 16;
        Bundle bundle = new Bundle();
        //往Bundle中存放数据
        bundle.putString("result", "这是信息");
        message.setData(bundle);
        handler.sendMessage(message);

    }



}
