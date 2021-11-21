package com.example.knowledge_android.android_animation.start_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class WelcomeActivity extends AppCompatActivity {

    protected LinearLayout mainLinearLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                //3秒后进入另外一个Activity
                startActivity(new Intent(WelcomeActivity.this, ScreenActivity.class));
                //TODO：目的是保证到了另外一个Activity时，点击回退键，不能重新回来，所以结束当前Activity.
                finish(); //结束自己
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除未处理的消息
        handler.removeMessages(1);
        //移除所有未处理的消息
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_animation);
        mainLinearLayout = findViewById(R.id.main_linearlayout);

        //发送延迟3秒，进入下一个界面的消息
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    /*
    旋转动画 RotateAnimation： 0 --> 360 视图的中心点
    透明度动画AlphaAnimation:  0 --> 1  2s
    缩放动画ScaleAnimation：0-->1 视图的中心点
     */
    public void showAnimation() {
        //旋转动画 RotateAnimation： 0 --> 360 视图的中心点
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //透明度动画AlphaAnimation:  0 --> 1  2s
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        //缩放动画ScaleAnimation：0-->1 视图的中心点
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        //创建复合动画并添加
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        //启动
        mainLinearLayout.startAnimation(animationSet);
    }
}
