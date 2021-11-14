package com.example.knowledge_android.android_service.localservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/*
 * 自定义本地服务
 * 1. startService(intent)
 *      第一次调用：-->构造方法()-->onCreate()-->onStartCommand()
 *      后面再调用(重要)：-->OnStartCommand()
 *    stopService()：-->onDestroy()
 *
 * 2. bindService(intent,serviceConnection)
 *      调用：-->构造方法-->onCreate()-->onBind()-->onServiceConnected()
 *    unbindService()：(只有当前Activity与Service连接) --> onUnbind()-->onDestroy()
 */
public class LocalService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder(); //这里创建的对象，会被传回Activity中的绑定服务方法
        //return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
