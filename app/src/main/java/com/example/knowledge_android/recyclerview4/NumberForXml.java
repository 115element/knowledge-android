package com.example.knowledge_android.recyclerview4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.knowledge_android.R;

import java.util.Objects;

/**
 * 泓远组件式用法
 */
public class NumberForXml extends LinearLayout implements View.OnClickListener {

    public NumberForXml(Context context) {
        super(context);
        inflate(context, R.layout.tree_phone, this);
        initView();//初始化view总的所有组件对象,并分别设置点击事件
    }

    private Button phoneButton;
    private TextView alreadyInputTextView;
    private PopupWindow treeNumberPop;

    private String number = "";

    /**
     * 绑定xml组件与java按键属性
     */
    @SuppressLint("SetTextI18n")
    void initView() {
        //手机号输入
        phoneButton = (Button) findViewById(R.id.inputPhone);
        phoneButton.setOnClickListener(this);
    }

    //处理所有组件对象的点击事件(包括button,textView),已实现OnClickListener接口的方式,回调方式无需手动调用
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Log.i("TAG","点击");
        switch (v.getId()) {

            case R.id.inputPhone:
                System.out.println("手机号");
                show_tree_number_popupWindow(v);
                break;

            case R.id.btn_0:
                System.out.println("btn_0");
                number += "0";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_0));
                break;

            case R.id.btn_1:
                System.out.println("btn_1");
                number += "1";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_1));
                break;

            case R.id.btn_2:
                System.out.println("btn_2");
                number += "2";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_2));
                break;

            case R.id.btn_3:
                System.out.println("btn_3");
                number += "3";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_3));
                break;

            case R.id.btn_4:
                System.out.println("btn_4");
                number += "4";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_4));
                break;

            case R.id.btn_5:
                System.out.println("btn_5");
                number += "5";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_5));
                break;

            case R.id.btn_6:
                System.out.println("btn_6");
                number += "6";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_6));
                break;

            case R.id.btn_7:
                System.out.println("btn_7");
                number += "7";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_7));
                break;

            case R.id.btn_8:
                System.out.println("btn_8");
                number += "8";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_8));
                break;

            case R.id.btn_9:
                System.out.println("btn_9");
                number += "9";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_9));
                break;

            case R.id.btn_dot:
                System.out.println("btn_dot");
                number += ".";
                //StateMachine.instance.processEvent(new POSButtonEvent(numberButton_dot));
                break;

            case R.id.btn_delete:
                System.out.println("btn_delete");
                if (!Objects.equals(number, "")) {
                    number = number.substring(0, number.length() - 1);
                }
                //StateMachine.instance.processEvent(new POSButtonEvent(new BackspaceButton()));
                break;

            case R.id.btn_enter:
                System.out.println("btn_enter");
                phoneButton.setText(number);
                phoneButton.postInvalidate();
                hide_tree_number_popupWindow();
                //StateMachine.instance.processEvent(new POSButtonEvent(new EnterButton()));
                break;

            case R.id.btn_clear:
                System.out.println("btn_clear");
                number = "";
                //StateMachine.instance.processEvent(new POSButtonEvent(new ClearButton()));
                break;

            default:
                System.out.println("can't find button ");
                break;
        }
        Log.i("TAG", number);
        if (Objects.equals(number, "")) {
            alreadyInputTextView.setText("");
        } else {
            alreadyInputTextView.setText(number);
        }
        alreadyInputTextView.postInvalidate();
    }

    /**
     * 显示界面并绑定组件的id
     */
    private void show_tree_number_popupWindow(View v) {
        ViewAndWindow viewAndWindow = AndroidDrawableUtil.drawPopupWindow(this, v, R.layout.keypad_popup, 0, 0);
        View inflate = viewAndWindow.getView();
        treeNumberPop = viewAndWindow.getPopupWindow();
        bindDigitalKeyboardButtonAndId(inflate);

        alreadyInputTextView = inflate.findViewById(R.id.already_input);
        if (alreadyInputTextView == null) {
            Log.i("TAG", "为什么找不到控件，，注意：inflate");
        }
    }


    private void hide_tree_number_popupWindow() {
        if (treeNumberPop != null) {
            treeNumberPop.dismiss();
        }
    }


    private void bindDigitalKeyboardButtonAndId(View inflate) {
        Button btn_0 = (Button) inflate.findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        Button btn_dot = (Button) inflate.findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);
        Button btn_delete = (Button) inflate.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        Button btn_clear = (Button) inflate.findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        Button btn_enter = (Button) inflate.findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
        Button btn_1 = (Button) inflate.findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        Button btn_2 = (Button) inflate.findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        Button btn_3 = (Button) inflate.findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        Button btn_4 = (Button) inflate.findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        Button btn_5 = (Button) inflate.findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        Button btn_6 = (Button) inflate.findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        Button btn_7 = (Button) inflate.findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        Button btn_8 = (Button) inflate.findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        Button btn_9 = (Button) inflate.findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
    }
}
