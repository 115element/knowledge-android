package com.example.knowledge_android.android_animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class AnimationActivity extends AppCompatActivity {

    ImageView running;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_animation_xml);

        running = findViewById(R.id.running);


        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCodeAlpha((ImageView) view);
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////
    //缩放动画
    public void startCodeScale(ImageView view) {
        //宽度从0.5到1.5，高度从0.0到1.0，缩放的圆心为顶部中心点，延迟1s开始，持续2s,最终还原
        ScaleAnimation animation = new ScaleAnimation(0.5f, 1.5f, 0, 1,
                Animation.ABSOLUTE, view.getWidth() / 2, Animation.ABSOLUTE, 0);

        //相对自己写法
        //ScaleAnimation animation = new ScaleAnimation(0.5f, 1.5f, 0, 1,
        //        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);

        //延迟1秒开始
        animation.setStartOffset(1000);
        //持续2秒
        animation.setDuration(2000);
        //最终还原
        animation.setFillBefore(true);
        //启动动画
        view.startAnimation(animation);
    }

    //缩放动画xml
    public void startCodeScaleXML(View view) {
        //加载动画xml定义文件
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_test);
        //启动动画
        view.startAnimation(animation);
    }


/////////////////////////////////////////////////////////////////////////////////

    //旋转动画
    public void startCodeRotate(ImageView view) {
        //以图片中心点为中心，从负90度到正90度，持续5秒
        RotateAnimation rotateAnimation = new RotateAnimation(-90, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(5000);
        view.startAnimation(rotateAnimation);
    }

    //旋转动画xml
    public void startCodeRotateXml(ImageView view) {
        //以左顶点为坐标，从正90度到负90度，持续5秒
        //加载动画xml定义文件
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_test);
        //启动动画
        view.startAnimation(animation);
    }


    /////////////////////////////////////////////////////////////////////////////////
    //透明度动画
    //完全透明：0
    //完全不透明：1
    public void startCodeAlpha(ImageView view) {
        //从完全透明到完全不透明，持续2秒
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);

        //设置动画播放次数
        animation.setRepeatCount(Animation.INFINITE);

        view.startAnimation(animation);
    }

    //透明度动画
    public void startCodeAlphaXml(ImageView view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_test);
        animation.setFillAfter(true); //视图为：动画停止之后的样子
        //启动动画
        view.startAnimation(animation);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //平移动画
    public void startCodeTranslate(ImageView view) {
        //向右移动一个自己的宽度，向下移动一个自己的高度，持续2秒
        TranslateAnimation animation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 1, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 1);
        animation.setDuration(2000);
        view.startAnimation(animation);
    }

    //平移动画-xml
    public void startCodeTranslateXml(ImageView view) {
        //从屏幕的右边逐渐回到原来的位置，持续2秒
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_test);
        //启动动画
        view.startAnimation(animation);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //复合动画
    public void startCodeAnimationSet(ImageView view) {
        //透明度从透明到不透明，持续2秒，接着进行旋转360度动画，持续1秒

        //1.创建透明动画并设置
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);
        //2.创建旋转动画并设置
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(2000); //延迟2秒后执行
        //3.创建复合动画对象
        AnimationSet animationSet = new AnimationSet(true); //各个子动画，是否共享变化率
        //4.添加两个动画
        animationSet.addAnimation(animation);
        animationSet.addAnimation(rotateAnimation);
        //5.启动复合动画对象
        view.startAnimation(animationSet);
    }

    //复合动画xml
    public void startCodeAnimationSetXml(ImageView view) {
        //透明度从透明到不透明，持续2秒，接着进行旋转360度动画，持续1秒
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.set_test);
        //启动动画
        view.startAnimation(animation);
    }

/////////////////////////////////////////////////////////////////////////////////

    //测试动画监听
    public void testAnimationListener(ImageView view) {
        //1.定义动画文件
        //2.加载动画文件得到动画对象
        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_test);

        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);

        //设置线性变化【动画变化率】
        animation.setInterpolator(new LinearInterpolator());

        //设置动画重复次数
        //animation.setRepeatCount(3);
        animation.setRepeatCount(Animation.INFINITE); //无限重复

        //设置动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("动画开启");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("动画结束");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("动画重复");
            }
        });

        //启动动画
        view.startAnimation(animation);
    }

    //////////////////////////////////////
    //图片轮播动画
    public void running(){
        AnimationDrawable animationDrawable = (AnimationDrawable) running.getBackground();
        animationDrawable.start(); //启动动画
        animationDrawable.stop(); //停止动画
    }

}
