package com.example.knowledge_android.dialog.alertdialog_listview;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;

public class BuildAlertDialog {

    public void build() {

        List<HyiPlu> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HyiPlu hyiPlu = new HyiPlu();
            hyiPlu.setPluId(i + "");
            hyiPlu.setName("测试数据");
            list.add(hyiPlu);
        }
        Context context = OneApplication.getInstance().getBaseContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View alertView = LayoutInflater.from(context).inflate(R.layout.plu_select_alertdialog,null);
        Button cancel = alertView.findViewById(R.id.plu_cancel);
        Button enter = alertView.findViewById(R.id.plu_enter);
        PluListView pluListView = alertView.findViewById(R.id.plu_list_view);
        PluListView.PluListAdapter pluListAdapter = new PluListView.PluListAdapter(list);
        pluListView.setAdapter(pluListAdapter);
        builder.setView(alertView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            //设置AlertDialog透明，解决设置圆角不生效问题。
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(0f); //AlertDialog下层的透明度
        }
        alertDialog.show();
        cancel.setOnClickListener(
                v -> {
                    Log.i("TAG", "[取消]选择的商品id：" + PluListView.pluId);
                    Log.i("TAG", "点击的券序号:");
                    alertDialog.dismiss();
                }
        );
        enter.setOnClickListener(v -> {
            Log.i("TAG", "[确定]选择的商品id：" + PluListView.pluId);
            Log.i("TAG", "点击的券序号:");
            alertDialog.dismiss();
        });
    }
}
