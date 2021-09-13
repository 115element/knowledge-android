package com.example.knowledge_android.activity5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview5.NumImageView;

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
