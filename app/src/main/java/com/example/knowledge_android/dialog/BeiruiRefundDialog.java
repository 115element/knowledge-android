package com.example.knowledge_android.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.knowledge_android.R;

public class BeiruiRefundDialog extends Dialog {

    public BeiruiRefundDialog(@NonNull Context context) {
        super(context, R.style.NoSurplusDialog);
        setContentView(R.layout.beirui_refund_dialog);
    }
}
