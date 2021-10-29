package com.example.knowledge_android.time_limit_job;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.example.knowledge_android.R;

public class TimeLimitJobComponent extends PopupWindow {

    View view;
    TextView message;
    ProgressBar progressBar;

    public TimeLimitJobComponent(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        view = inflater.inflate(R.layout.time_limit_job_xml, null);
        message = view.findViewById(R.id.message);
        progressBar = view.findViewById(R.id.progress_bar);

        this.setContentView(view);// 设置View
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(false); //设置点击窗体自动关闭
        this.setOutsideTouchable(false); //设置点击窗体侧边关闭窗体
        this.setAnimationStyle(android.R.style.Animation_Dialog);
        //this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        //this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
    }


//    @OnUIThread
//    void showPopup() {
//        this.showAtLocation(view, Gravity.CENTER, 0, 0);
//    }
//
//    @OnUIThread
//    void hidePopup() {
//        this.dismiss();
//    }
}
