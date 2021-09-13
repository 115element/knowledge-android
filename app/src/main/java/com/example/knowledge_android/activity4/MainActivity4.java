package com.example.knowledge_android.activity4;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview4.AndroidDrawableUtil;
import com.example.knowledge_android.recyclerview4.ViewAndWindow;

import java.util.Objects;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {

    private Button inputPhone;
    private TextView alreadyInputTextView;
    private PopupWindow keypadPopup;
    private String number = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tree_phone);
        initView();
    }


    @SuppressLint("SetTextI18n")
    void initView() {
        //输入点击框
        inputPhone = (Button) findViewById(R.id.inputPhone);
        inputPhone.setOnClickListener(this);
    }


    //处理所有组件对象的点击事件(包括button,textView),已实现OnClickListener接口的方式,回调方式无需手动调用
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Log.i("TAG", "点击");
        switch (v.getId()) {

            case R.id.inputPhone:
                System.out.println("手机号");
                show_tree_number_popupWindow(v);
                break;

            case R.id.btn_0:
                System.out.println("btn_0");
                number += "0";
                break;

            case R.id.btn_1:
                System.out.println("btn_1");
                number += "1";
                break;

            case R.id.btn_2:
                System.out.println("btn_2");
                number += "2";
                break;

            case R.id.btn_3:
                System.out.println("btn_3");
                number += "3";
                break;

            case R.id.btn_4:
                System.out.println("btn_4");
                number += "4";
                break;

            case R.id.btn_5:
                System.out.println("btn_5");
                number += "5";
                break;

            case R.id.btn_6:
                System.out.println("btn_6");
                number += "6";
                break;

            case R.id.btn_7:
                System.out.println("btn_7");
                number += "7";
                break;

            case R.id.btn_8:
                System.out.println("btn_8");
                number += "8";
                break;

            case R.id.btn_9:
                System.out.println("btn_9");
                number += "9";
                break;

            case R.id.btn_dot:
                System.out.println("btn_dot");
                number += ".";
                break;

            case R.id.btn_delete:
                System.out.println("btn_delete");
                if (!Objects.equals(number, "")) {
                    number = number.substring(0, number.length() - 1);
                }
                break;

            case R.id.btn_enter:
                System.out.println("btn_enter");
                inputPhone.setText(number);
                inputPhone.postInvalidate();
                hide_tree_number_popupWindow();
                break;

            case R.id.btn_clear:
                System.out.println("btn_clear");
                number = "";
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
        ViewAndWindow viewAndWindow = AndroidDrawableUtil.drawPopupWindow(inputPhone, v, R.layout.keypad_popup, 0, 0);
        View inflate = viewAndWindow.getView();
        keypadPopup = viewAndWindow.getPopupWindow();
        bindDigitalKeyboardButtonAndId(inflate);

        alreadyInputTextView = inflate.findViewById(R.id.already_input);
        if (alreadyInputTextView == null) {
            Log.i("TAG", "为什么找不到控件，，注意：inflate");
        }
    }


    private void hide_tree_number_popupWindow() {
        if (keypadPopup != null) {
            keypadPopup.dismiss();
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
