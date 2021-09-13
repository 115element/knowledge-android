package com.example.knowledge_android.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class JumpAvtivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jump_layout);
        textView=findViewById(R.id.jump_textview);
        Intent intent=getIntent();
        String caption=intent.getStringExtra("caption");
        textView.setText(caption);
    }
}

