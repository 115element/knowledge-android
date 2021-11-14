package com.example.knowledge_android.android_service.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ABCActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void play(){
        Intent intent = new Intent(this,MusicService.class);
        intent.putExtra("action","play");
        startActivity(intent);
    }


    //(首先要引入AIDL文件) 才能用功能挂断电话
    public void endCall() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //通过反射调用隐藏的API
        //得到隐藏类的Class对象
        Class<?> aClass = Class.forName("android.os.ServiceManager");

        //得到方法对应的Method对象
        Method method = aClass.getMethod("getService", String.class);

        //调用方法
        IBinder iBinder = (IBinder) method.invoke(null, Context.TELECOM_SERVICE);

        //以下方法需要引入AIDL才能使用
        //ITelephony telephony = ITelephony.Stub.asInterface(iBinder);
        //telephony.endCall();
    }
}
