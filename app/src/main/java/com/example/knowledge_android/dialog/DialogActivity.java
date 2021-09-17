package com.example.knowledge_android.dialog;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_15);

        //窗体样式1
//        PayingDialog payingDialog = new PayingDialog(this);
//        payingDialog.show();

        //窗体样式2
//        SingleSureDialog singleSureDialog = new SingleSureDialog(this);
//        singleSureDialog.show();

        //窗体样式3
        SureOrCancelDialog sureOrCancelDialog = new SureOrCancelDialog(this);
        sureOrCancelDialog.show();
    }
}
