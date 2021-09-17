package com.example.knowledge_android.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.example.knowledge_android.R;


/**
 * 2021/9/14
 * 微信支付宝 , 请出示付款码弹出对话框
 */
public class PayingDialog extends Dialog implements View.OnClickListener {

    public PayingDialog(Context context) {
        super(context, R.style.NoSurplusDialog);
        setContentView(R.layout.united_pay_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //初始化界面控件
        initView();
    }


    /**
     * 初始化界面控件
     */
    private void initView() {
        /**
         * 取消按键
         */
        ImageButton cancelButton = (ImageButton) findViewById(R.id.close_button);
        cancelButton.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_button:
                this.dismiss();
                break;

            default:
                break;
        }

    }

}
