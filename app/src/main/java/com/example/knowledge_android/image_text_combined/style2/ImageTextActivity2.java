package com.example.knowledge_android.image_text_combined.style2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class ImageTextActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_13);

        ImageView imageView = findViewById(R.id.imageView2);
        TextView textView = findViewById(R.id.textView);

        imageView.setImageResource(R.drawable.b);

        textView.setText("飒飒扩大开始考试");
    }
}
