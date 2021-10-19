package com.example.knowledge_android.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

/**
 * PopupWindow为非模态，可以继续操作弹出界面之下的控件；
 * Dialog为模态，必须先取消Dialog才能操作Dialog之下的控件；
 */

public class AStudyDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_16);

        //窗体样式1
//        PayingDialog payingDialog = new PayingDialog(this);
//        payingDialog.show();

        //窗体样式2
//        SingleSureDialog singleSureDialog = new SingleSureDialog(this);
//        singleSureDialog.show();

        //窗体样式3
//        SureOrCancelDialog sureOrCancelDialog = new SureOrCancelDialog(this);
//        sureOrCancelDialog.show();



        //窗体样式4
//        TranslucentDialog translucentDialog = new TranslucentDialog(this);
//        Window window = translucentDialog.getWindow();
//        if (window != null) {
//            //TODO 重要：设置弹窗框透明度，可以看见弹框后边的东西
//            WindowManager.LayoutParams lp = window.getAttributes();
//            //为了好看，有时候需要设置dialog透明。自定义dialog只需要几家代码就能实现背景透明。
//            //设置背景透明度,背景透明,参数为0到1之间。0表示完全透明，1就是不透明。按需求调整参数
//            lp.alpha = 0.5f;
//            window.setAttributes(lp);
//            //设置AlertDialog透明，解决设置圆角不生效问题。
//            //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            //window.setDimAmount(0.8f); //AlertDialog下层的透明度
//        }
//        translucentDialog.show();



        //窗体样式5  【半透明的弹窗】
//        RefundDialog refundDialog = new RefundDialog(this);
//        Window window1 = refundDialog.getWindow();
//        if (window1 != null) {
//            //TODO 下边三行代码，才是透明弹窗起效果的原因【半透明的弹窗】
//            WindowManager.LayoutParams lp = window1.getAttributes();
//            lp.alpha = 0.5f;
//            window1.setAttributes(lp);
//        }
//        refundDialog.show();


        //窗体样式5  【ConstraintLayout布局半透明弹窗】
        BeiruiRefundDialog beiruiRefundDialog = new BeiruiRefundDialog(this);
        Window window = beiruiRefundDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.5f;
            window.setAttributes(lp);
        }
        beiruiRefundDialog.show();





        //TODO  学习this、super、class用法。
        AStudyDialogActivity dialogActivity = AStudyDialogActivity.this;
        if (dialogActivity == this) {
            Log.i("A","二者是相等的");
        }
        Activity parent = AStudyDialogActivity.super.getParent();
        Class<AStudyDialogActivity> dialogActivityClass = AStudyDialogActivity.class;
        Class<? extends AStudyDialogActivity> aClass = this.getClass();
        if (aClass == dialogActivityClass) {
            Log.i("B","二者是相等的");
        }
    }



    //android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
    //该方法需要在Activity加载完毕后，才可以调用，否则会报错哦。
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        View alertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.beirui_refund_dialog, null);
        builder.setView(alertView);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setDimAmount(0f); //AlertDialog下层的透明度
        }
        alertDialog.show();
    }

}
