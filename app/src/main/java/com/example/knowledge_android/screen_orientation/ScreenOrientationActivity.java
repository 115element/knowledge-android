package com.example.knowledge_android.screen_orientation;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

/*
 *
 * 当该Activity加载后，
 * 此时，切换屏幕旋转，那么会执行 onDestroy()方法 --> onCreate()方法。
 * 也就是Activity会重建。
 *
 * 如何解决这个问题呢？
 * 需要再AndroidManifest.xml中该Activity中增加配置：
 * android:configChanges="orientation|keyboardHidden|screenSize"
 */

public class ScreenOrientationActivity extends AppCompatActivity implements View.OnClickListener {


    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_orientation_xml);
        button = findViewById(R.id.switch_x);
        button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //当屏幕方向改变了，那么会调用此方法。
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //ORIENTATION_PORTRAIT   肖像
        //ORIENTATION_LANDSCAPE  风景画
        int i = newConfig.orientation;
        if (i == ORIENTATION_PORTRAIT) {
            //竖屏
        }
        if (i == ORIENTATION_LANDSCAPE) {
            //恒横屏
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        //1.得到当前APP方向
        int i = getResources().getConfiguration().orientation;

        //2.设置新的方向
        if (i == ORIENTATION_PORTRAIT) {
            //如果当前竖屏，切换为横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            //如果当前横屏，切换为竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
