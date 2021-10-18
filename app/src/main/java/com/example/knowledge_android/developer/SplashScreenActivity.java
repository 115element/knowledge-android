package com.example.knowledge_android.developer;

import static com.arasthel.swissknife.dsl.AndroidContextDSL.intent;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startActivity(intent(getBaseContext(), MainActivity.class));  // 开启MainActivity
        finish();
    }
}
