package com.example.knowledge_android.android_event.key_enent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class KeyEventActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        //如果点击返回键怎么处理，相当于监听back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //显示确定的Dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            builder.setMessage("你确定退出吗");
            builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //点击退出回调
                }
            }).setNegativeButton("再看看", null).show();

            return true; //不会退出了
        }
        return super.onKeyUp(keyCode, event);
    }
}
