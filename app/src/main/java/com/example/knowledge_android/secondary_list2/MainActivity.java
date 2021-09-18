package com.example.knowledge_android.secondary_list2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_show_one_btn:
                startActivity(new Intent(this, EmptyShowOneActivity.class));
                break;
            case R.id.empty_show_two_btn:
                startActivity(new Intent(this, EmptyShowTwoActivity.class));
                break;
            case R.id.empty_show_three_btn:
                startActivity(new Intent(this, EmptyShowThreeActivity.class));
                break;
            default:
                break;
        }
    }
}