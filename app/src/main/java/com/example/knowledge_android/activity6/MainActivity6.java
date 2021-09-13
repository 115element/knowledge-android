package com.example.knowledge_android.activity6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity6 extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main_4);

        //为FrameLayout设置角标，而不是给Button设置角标
        FrameLayout fm_btn_badge = findViewById(R.id.fm_btn_badge);
        //Button用于事件操作
        Button btn_badge = findViewById(R.id.btn_badge);

        //设置角标
        QBadgeView badgeView1 = new QBadgeView(this);
        badgeView1.bindTarget(fm_btn_badge);
        //badgeView1.setBadgeNumber(100);
        badgeView1.setBadgeText("+￥"+"3元");
        //badgeView1.setBadgeGravity(Gravity.END | Gravity.TOP);
    }


    //其中角标还是有不少参数滴，放一些参数在下面
    /**
     * setBadgeNumber	设置Badge数字
     * setBadgeText	设置Badge文本
     * setBadgeTextSize	设置文本字体大小
     * setBadgeTextColor	设置文本颜色
     * setExactMode	设置是否显示精确模式数值
     * setBadgeGravity	设置Badge相对于TargetView的位置，目前只支持   Gravity.START | Gravity.TOP ,
     * Gravity.END | Gravity.TOP ,Gravity.START | Gravity.BOTTOM , Gravity.END | Gravity.BOTTOM ,
     * Gravity.CENTER , Gravity.CENTER | Gravity.TOP , Gravity.CENTER | Gravity.BOTTOM ,
     * Gravity.CENTER | Gravity.START , Gravity.CENTER | Gravity.END
     *否则报错哦
     * setGravityOffset	设置外边距
     * setBadgePadding	设置内边距
     * setBadgeBackgroundColor	设置背景色
     * setBadgeBackground	设置背景图片
     * setShowShadow	设置是否显示阴影
     * setOnDragStateChangedListener	打开拖拽消除模式并设置监听
     * stroke	描边
     * hide	隐藏Badge
     */

}
