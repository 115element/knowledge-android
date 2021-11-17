package com.example.knowledge_android.android_animation.setup_guide;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class SetupGuide1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_guide1_xml);
    }

    public void next() {
        startActivity(new Intent(this, SetupGuide2Activity.class));
        //指定Activity切换的动画，默认是没有动画的
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
