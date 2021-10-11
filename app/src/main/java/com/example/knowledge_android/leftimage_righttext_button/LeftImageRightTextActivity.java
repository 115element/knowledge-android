package com.example.knowledge_android.leftimage_righttext_button;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class LeftImageRightTextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leftimage_righttext);

        Button b = findViewById(R.id.delete);

        b.setText("这是一个按钮");
        b.setBackgroundColor(Color.WHITE);
        b.setTextColor(Color.BLACK);
        //b.setAutoSizeTextTypeUniformWithConfiguration(5,20,1,TypedValue.COMPLEX_UNIT_SP);
        ViewTreeObserver vto2 = b.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                b.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int w1 = b.getWidth();
                int h1 = b.getHeight();

                if (!TextUtils.isEmpty(b.getText())) {
                    if (b.getText().length() > 5) {
                        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(w1 / 2 as int, ViewGroup.LayoutParams.WRAP_CONTENT);
                        //b.setLayoutParams(layoutParams);
                        //b.setGravity(Gravity.RIGHT);
                        b.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END); //TODO 设置文字靠右显示，重要
                        //b.setGravity(Gravity.END)
                        //b.setPadding(1, 0, 0, 0);
                    }

                    Log.i("宽", "w:" + w1);
                    Log.i("高", "h:" + h1);
                    Drawable d = getResources().getDrawable(R.drawable.init);
//                            if ("店号设置".equals(b.getText())) {
//                                d = getResources().getDrawable(R.mipmap.store_no);
//                            }
//                            if ("机号设置".equals(b.getText())) {
//                                d = getResources().getDrawable(R.mipmap.pos_no);
//                            }
//                            if ("中台设置".equals(b.getText())) {
//                                d = getResources().getDrawable(R.mipmap.init);
//                            }
//                            if ("初始化".equals(b.getText())) {
//                                d = getResources().getDrawable(R.mipmap.init);
//                            }
                    int x1 = w1 / 7 * 1;
                    int x2 = w1 / 7 * 2;
                    int y1 = h1 / 3 * 1;
                    int y2 = h1 / 3 * 2;
//                            log.info("xxxxx1:" + x1)
//                            log.info("yyyyy1:" + y1)
//                            log.info("xxxxx2:" + x2)
//                            log.info("yyyyy2:" + y2)
                    //left -矩形左侧的X坐标
                    //top -矩形顶部的Y坐标
                    //right -矩形右侧的X坐标
                    //bottom -矩形底部的Y坐标
                    d.setBounds(x1, 5, x2, y2 - 5);
                    b.setCompoundDrawables(d, null, null, null); //将图片绘制在按钮左侧
                } else {
                    b.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
