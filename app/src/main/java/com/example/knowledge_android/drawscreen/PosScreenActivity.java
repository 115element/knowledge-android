package com.example.knowledge_android.drawscreen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;

public class PosScreenActivity extends AppCompatActivity {

    /**
     * 碎片（Fragment）是一种可以嵌入在活动当中的UI片段
     **/
    //PosScreen posScreen; /**碎片（Fragment）是一种可以嵌入在活动当中的UI片段**/
    Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置当前界面
        setContentView(R.layout.activity_main);


        // getSupportFragmentManager()主要用于支持3.0以下android系统API版本，
        // 3.0以上系统可以直接调用getFragmentManager() ，因为fragment是3.0以后才出现的组件
        // FragmentManager顾名思义是用来管理fragment的.
        // getSupportFragmentManager()就是获取所在fragment 的父容器的管理器，getFragmentManager()同理。
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        /** 添加Fragment栈改变监听器*/
        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.i("TAG", "onBackStackChanged....");
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.container);
                fragmentById.setUserVisibleHint(true);/**告诉系统该Fragment可见*/
            }
        });

        //创建PosScreen[Fragment]
        PosScreen posScreen = new PosScreen();
        fragment = posScreen;
        OneApplication.getInstance().setPosScreen(posScreen);

        //将当前Fragment替换为另外一个Fragment
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "currentFragment");
        fragmentTransaction.commit();
    }




    /*
      我们在android开发中经常会用到fragment，例如侧拉栏的切换，viewPager的切换。
      而我们切换fragment无非就两种方法：
              1.replace（）；
              2.add（），hide（），show（）；
      两种发方法相比较而言，我更加推荐使用第二种方法，因为fragmentTransaction的replace（）方法实际上就是remove（）和add（）的集合，
      每一次fragment的切换都要销毁视图，然后重新创建一个fragment实例，调用fragment的整个生命周期，这样对于性能上来说不是很好。

      以下是使用第二种切换fragment方法的函数：
     */
    private void changeFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            fromFragment = toFragment;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (toFragment.isAdded() == false) {
            ft.hide(fromFragment).add(R.id.container, toFragment).commit();
        } else {
            ft.hide(fromFragment).show(toFragment).commit();
        }
    }


    /**
     * Handler是Android SDK来处理异步消息的核心类。
     * 子线程与主线程通过Handler来进行通信。子线程可以通过Handler来通知主线程进行UI更新。
     **/
    private Handler myTimeHandler = new Handler() {
//        void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                sendEmptyMessageDelayed(0, 1000);
//            }
//        }
    };
}
