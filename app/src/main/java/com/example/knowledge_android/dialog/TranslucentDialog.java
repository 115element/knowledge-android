package com.example.knowledge_android.dialog;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.knowledge_android.R;


/**
 * 2021/9/14
 * 自定义的弹窗
 */
public class TranslucentDialog extends Dialog implements View.OnClickListener {

    public TranslucentDialog(Context context) {
        super(context, R.style.NoSurplusDialog);

        setContentView(R.layout.translucent_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //初始化界面控件
        initView();
    }

    //(重要 : 对话框内的内容)
    private TextView msgTextView;

    public TextView getMsgTextView() {
        return msgTextView;
    }

    public void setMsgTextView(TextView msgTextView) {
        this.msgTextView = msgTextView;
    }

    /**
     * 初始化界面控件
     */
    private void initView() {

        //msg 对话框内容
        msgTextView = (TextView) findViewById(R.id.msg);

        /*
         * 确定按键
         */
        Button sureButton = (Button) findViewById(R.id.sure_btn);
        sureButton.setOnClickListener(this);

        /*
         * 取消按键
         */
        Button cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //确认键
            case R.id.sure_btn:
                Log.i("TAG","大啊啊");
                break;

            //取消按键
            case R.id.cancel_btn:
                Log.i("1","1");
                break;

            default:
                Log.e(TAG, "没有指定按键");
                break;
        }

    }
}
