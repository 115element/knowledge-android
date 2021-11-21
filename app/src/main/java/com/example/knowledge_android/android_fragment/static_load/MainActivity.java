package com.example.knowledge_android.android_fragment.static_load;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.knowledge_android.R;

/*
测试使用Fragment(静态加载)
1.定义Fragment的子类，并加载一个布局文件。
2.在布局文件中通过<fragment>指定自定义Fragment。
3.我们的Activity必须继承于FragmentActivity。
每个Fragment本质上都会生成一个FrameLayout，它加载的布局为其子布局。
 */

public class MainActivity extends FragmentActivity {

    public MainActivity(){
        Log.i("TAG","construct");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_xml);
    }
}
