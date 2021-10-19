package com.example.knowledge_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.knowledge_android.R;

public class RefundDialog extends Dialog implements View.OnClickListener {

    public RefundDialog(@NonNull Context context) {
        super(context,R.style.NoSurplusDialog);
        setContentView(R.layout.refund_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }



    @Override
    public void onClick(View view) {

    }
}
