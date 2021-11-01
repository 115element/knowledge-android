package com.example.knowledge_android.time_limit_job;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class TimeLimitJobActivity extends AppCompatActivity {

    TextView message;
    ProgressBar progressBar;

    //完成进度
    private int mProcessStatus = 0;
    //声明一个用于处理消息的Handler类的对象
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定布局
        setContentView(R.layout.time_limit_job_xml);
        message = findViewById(R.id.message);
        progressBar = findViewById(R.id.progress_bar);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x111) {
                    progressBar.setProgress(mProcessStatus);//更新进度
                } else {
                    Toast.makeText(TimeLimitJobActivity.this, "耗时操作已经完成", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        };


        Handler handler = new Handler(Looper.getMainLooper()){ // 解决以上无参数构造方法废弃问题
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x111) {
                    progressBar.setProgress(mProcessStatus);//更新进度
                } else {
                    Toast.makeText(TimeLimitJobActivity.this, "耗时操作已经完成", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        };



        //开个线程用语模拟耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProcessStatus = doWork();//获取耗时操作完成的百分比
                    Message msg = new Message();
                    if (mProcessStatus < 100) {
                        msg.what = 0x111;
                        mHandler.sendMessage(msg);//发送消息
                    } else {
                        msg.what = 0x110;
                        mHandler.sendMessage(msg);//发送消息
                        break;
                    }
                }
            }

            //模拟一个耗时操作
            private int doWork() {
                mProcessStatus += Math.random() * 10;//改变完成进度
                try {
                    Thread.sleep(200);//每隔200毫秒进度改变一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return mProcessStatus;//返回新的进度
            }
        }).start();//开启一个新的线程

    }
}
