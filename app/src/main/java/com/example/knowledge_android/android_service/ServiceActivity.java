package com.example.knowledge_android.android_service;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.android_service.localservice.LocalService;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //开启服务
    @SuppressLint("WrongConstant")
    public void startMyService() {
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        Toast.makeText(this, "启动了一个服务", 1).show();
    }

    //停止服务
    @SuppressLint("WrongConstant")
    public void stopMyService() {
        Intent intent = new Intent(this, LocalService.class);
        stopService(intent);
        Toast.makeText(this, "停止了一个服务", 1).show();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ServiceConnection serviceConnection;

    //绑定服务
    public void bindMyService() {
        Intent intent = new Intent(this, LocalService.class);
        if (serviceConnection != null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    //当Activity和Service建立连接时调用
                    // iBinder 该参数由服务传回
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    //当Activity和Service关闭连接时调用
                }
            };

            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            System.out.println("已经绑定了服务");
        }
    }

    //解绑服务
    public void unbindMyService() {
        if (serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        } else {
            System.out.println("还没有绑定服务");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //在Activity销毁时调用
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }
    }
}
