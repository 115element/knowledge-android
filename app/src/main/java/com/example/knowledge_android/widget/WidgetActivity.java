package com.example.knowledge_android.widget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.widget.view.SoundButtonView;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_layout);

        SoundButtonView soundButtonView = findViewById(R.id.sbv_view);
        soundButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundButtonView.playClickingSound();
            }
        });
    }
}
