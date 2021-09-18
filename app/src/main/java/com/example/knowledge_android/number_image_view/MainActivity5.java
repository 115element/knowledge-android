package com.example.knowledge_android.number_image_view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.number_image_view.NumImageView;

public class MainActivity5 extends AppCompatActivity {

    private NumImageView numImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_3);
        numImageView = (NumImageView) findViewById(R.id.actionbar_btn);
        numImageView.setNum(100);
    }
}
