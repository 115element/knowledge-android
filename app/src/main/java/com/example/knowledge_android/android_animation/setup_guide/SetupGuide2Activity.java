package com.example.knowledge_android.android_animation.setup_guide;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class SetupGuide2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_guide2_xml);
    }

    public void pre(){
        startActivity(new Intent(this,SetupGuide1Activity.class));
        //指定Activity切换的动画，默认是没有动画的
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void over(){
        finish();
        //指定Activity切换的动画，默认是没有动画的
        overridePendingTransition(R.anim.bottom_in, R.anim.disappear_out);
    }

}
