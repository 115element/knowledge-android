package com.example.knowledge_android.android_animation.shake_animation;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class ShakeActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button7);
        button.setOnClickListener(v -> {
            start();
        });
    }

    public void start() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(animation);
    }

}
