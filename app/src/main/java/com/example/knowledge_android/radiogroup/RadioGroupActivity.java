package com.example.knowledge_android.radiogroup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;


public class RadioGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_button);

//        Button btn = (Button) findViewById(R.id.bt);
//        btn.setVisibility(View.VISIBLE);
//        BadgeView badge4 = new BadgeView(this, btn);
//        badge4.setText("...");
//        badge4.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        badge4.show();
    }
}
