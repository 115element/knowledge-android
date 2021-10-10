package com.example.knowledge_android.flexbox_layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.google.android.flexbox.FlexboxLayout;

public class FlexBoxActivity2 extends AppCompatActivity {

    String[] tags = {"吃货", "逗比", "创业者", "上班这点事儿",
            "影视天堂", "大学生活", "单身狗", "运动和健身",
            "网购达人", "旅游", "程序员", "追星族", "短篇小说",
            "美食", "教育", "学生党", "汪星人"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flex_box_layout_test);

        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);

        for (int i = 0;i < tags.length;i++) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.text_view,null);
            TextView textview = (TextView) layout.findViewById(R.id.textview);
            textview.setBackgroundResource(R.drawable.shape_text);
            textview.setText(tags[i]);
            flexboxLayout.addView(layout);
        }

    }
}
