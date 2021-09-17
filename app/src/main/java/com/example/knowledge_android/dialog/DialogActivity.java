package com.example.knowledge_android.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

/**
 * PopupWindow为非模态，可以继续操作弹出界面之下的控件；
 * Dialog为模态，必须先取消Dialog才能操作Dialog之下的控件；
 */

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


        //TODO  学习this、super、class用法。
        DialogActivity dialogActivity = DialogActivity.this;
        if (dialogActivity == this) {
            Log.i("A","二者是相等的");
        }
        Activity parent = DialogActivity.super.getParent();
        Class<DialogActivity> dialogActivityClass = DialogActivity.class;
        Class<? extends DialogActivity> aClass = this.getClass();
        if (aClass == dialogActivityClass) {
            Log.i("B","二者是相等的");
        }
    }
}
