package com.example.knowledge_android.android_fragment.dynamic_load;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.knowledge_android.R;

/*
测试使用Fragment（动态使用）
1.使用FragmentManager和FragmentTransaction动态使用一个Fragment
2.方式：
    add(viewId , fragment)：将fragment的视图添加为指定视图的子视图(加在原有子视图的后面)
    replace(viewId , fragment)：将fragment的视图添加为指定视图的子视图（先remove原有的子视图）
    remove(fragment)：将fragment的视图移除

 */

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_dynamic_xml);

        //创建Fragment对象
        MyFragment1 fragment1 = new MyFragment1();
        //得到FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //得到FragmentTransaction
        FragmentTransaction transaction = manager.beginTransaction();
        //添加Fragment对象,并提交
        transaction.add(R.id.ll_main_container, fragment1).commit();
    }

    MyFragment2 fragment2;
    public void showFragment2(View view) {
        //创建Fragment对象
        fragment2 = new MyFragment2();
        //得到FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //得到FragmentTransaction
        FragmentTransaction transaction = manager.beginTransaction();

        //TODO 将当前操作添加到回退栈,这样就能达到，点击回退键回到上一个状态，否则就退出了
        transaction.addToBackStack(null);

        //替换Fragment对象,并提交  //替换的原理，就是先移除再添加
        transaction.replace(R.id.ll_main_container, fragment2).commit();

    }

    public void deleteFragment2(View view) {
        //得到FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        //得到FragmentTransaction
        FragmentTransaction transaction = manager.beginTransaction();
        //删除Fragment对象,并提交
        transaction.remove(fragment2).commit();
    }
}
